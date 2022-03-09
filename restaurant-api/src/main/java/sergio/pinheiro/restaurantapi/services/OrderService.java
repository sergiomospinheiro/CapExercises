package sergio.pinheiro.restaurantapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sergio.pinheiro.restaurantapi.models.Order;
import sergio.pinheiro.restaurantapi.models.OrderStatus;
import sergio.pinheiro.restaurantapi.repositories.OrderRepository;

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

//	public String getCustomerName(Integer orderId) {
//		return orderRepository.getCustomerNameByOrderId(orderId);
//	}

}
