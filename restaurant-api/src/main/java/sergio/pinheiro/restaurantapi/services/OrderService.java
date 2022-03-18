package sergio.pinheiro.restaurantapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sergio.pinheiro.restaurantapi.dtos.OrderDto;
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

	public Order getOrder(Integer orderId) {
		return orderRepository.findById(orderId).get();
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

	public boolean changeOrderStatus(OrderStatus orderStatus) {

		OrderDto orderDto = new OrderDto();

		Order order = new Order();

		OrderResponse<?> orderResponse = new OrderResponse<>();

		if (orderDto.getOrderStatus().equals(OrderStatus.ORDER_PLACED)
				|| order.getOrderStatus().equals(OrderStatus.DELIVERED)
				|| (order.getOrderStatus() == (OrderStatus.ORDER_CANCELLED))) {

			orderResponse = orderResponse.sendNotOkResponse("We are sorry but that change can't be made");
			// flagCheck = false;
		}

		switch (orderDto.getOrderStatus()) {
		case BEING_PREPARED:
			if (order.getOrderStatus() == OrderStatus.ORDER_PLACED) {
				order.setOrderStatus(OrderStatus.BEING_PREPARED);
				// flagCheck = true;
			} else {
				orderResponse = orderResponse.sendNotOkResponse("Purchase Status not Allowed");
				// flagCheck = false;
			}
			break;
		case ON_THE_WAY:
			if (order.getOrderStatus() == OrderStatus.BEING_PREPARED) {
				order.setOrderStatus(OrderStatus.ON_THE_WAY);
				// flagCheck = true;
			} else {
				orderResponse = orderResponse.sendNotOkResponse("Purchase Status not Allowed");
				// flagCheck = false;
			}
			break;
		case DELIVERED:
			if (order.getOrderStatus() == OrderStatus.ON_THE_WAY) {
				order.setOrderStatus(OrderStatus.DELIVERED);
				// flagCheck = true;

			} else {
				orderResponse = orderResponse.sendNotOkResponse("Purchase Status not Allowed");
				// flagCheck = false;
			}

			break;
		case ORDER_CANCELLED:
			if (order.getOrderStatus() != OrderStatus.ON_THE_WAY && order.getOrderStatus() != OrderStatus.DELIVERED) {
				order.setOrderStatus(OrderStatus.ORDER_CANCELLED);
				// flagCheck = true;

			} else {
				orderResponse = orderResponse.sendNotOkResponse("Purchase Status not Allowed");
				// flagCheck = false;
			}
		default:
			System.out.println("The order has been delivered!"); // ?
			break;
		}
		return true;

	}

}
