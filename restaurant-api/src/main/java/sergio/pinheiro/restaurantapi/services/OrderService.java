package sergio.pinheiro.restaurantapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sergio.pinheiro.restaurantapi.models.Order;
import sergio.pinheiro.restaurantapi.models.OrderStatus;
import sergio.pinheiro.restaurantapi.repositories.OrderRepository;
import sergio.pinheiro.restaurantapi.responses.OrderResponse;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public Order save(Order order) {
		return orderRepository.saveAndFlush(order);
	}

	public void delete(Order order) {
		orderRepository.delete(order);
	}

	public Order getOrder(String string) {
		return orderRepository.findByTransactionId(string).get();
	}

	public boolean existsById(Integer orderId) {
		return orderRepository.existsById(orderId);

	}

	public OrderStatus getOrderStatus(Integer orderId) {
		Order order = orderRepository.getById(orderId);
		return order.getOrderStatus();
	}

	public void deleteById(Integer orderId) {
		orderRepository.deleteById(orderId);

	}

	public boolean existsByCustomerName(String customerName, String cutomerDB) {

		return orderRepository.existsByCustomerName(customerName);
	}

	// dividir em 2 métodos ( validação e mudança\switch)
	public boolean isOrderStatusValid(OrderStatus orderStatusModel) {

		boolean orderStatusValidation = false;

		OrderResponse<?> orderResponse = new OrderResponse<>();

		// took out -> orderStatusDto.equals(OrderStatus.ORDER_PLACED

		if (orderStatusModel.equals(OrderStatus.DELIVERED) || (orderStatusModel == (OrderStatus.ORDER_CANCELLED))) {

			orderResponse = orderResponse.sendNotOkResponse("Order can't be changed"); // this message is sent in the
																						// controller also
			orderStatusValidation = false;
		} else {
			orderStatusValidation = true;
		}

		return orderStatusValidation;

	}

	public boolean changeOrderStatus(OrderStatus orderStatusDto, OrderStatus orderStatusModel) {

		boolean orderStatusChangeable = false;
		OrderResponse<?> orderResponse = new OrderResponse<>();

		switch (orderStatusDto) {
		case ORDER_PLACED:
			orderResponse = orderResponse.sendNotOkResponse("Order is already placed");
			break;
		case BEING_PREPARED:
			if (orderStatusModel == OrderStatus.ORDER_PLACED) {
				orderStatusChangeable = true;
			} else {
				orderResponse = orderResponse.sendNotOkResponse("Purchase Status not Allowed");
			}
			break;
		case ON_THE_WAY:
			if (orderStatusModel == OrderStatus.BEING_PREPARED) {
				orderStatusChangeable = true;
			} else {
				orderResponse = orderResponse.sendNotOkResponse("Purchase Status not Allowed");
				// orderStatusValidation = false;
			}
			break;
		case DELIVERED:
			if (orderStatusModel == OrderStatus.ON_THE_WAY) {
				orderStatusChangeable = true;
				// orderStatusValidation = true;

			} else {
				orderResponse = orderResponse.sendNotOkResponse("Purchase Status not Allowed");
				// orderStatusValidation = false;
			}

			break;
		case ORDER_CANCELLED:
			if (orderStatusModel != OrderStatus.ON_THE_WAY && orderStatusModel != OrderStatus.DELIVERED) {
				orderStatusChangeable = true;

			} else {
				orderResponse = orderResponse.sendNotOkResponse("Purchase Status not Allowed");
				// orderStatusValidation = false;
			}
		default:
			System.out.println("The order has been delivered!"); // ?
			break;
		}
		return orderStatusChangeable;

	}

}
