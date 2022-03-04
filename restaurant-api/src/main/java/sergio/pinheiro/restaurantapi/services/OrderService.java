package sergio.pinheiro.restaurantapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sergio.pinheiro.restaurantapi.models.Order;
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

//	public OrderStatus getOrderStatus(Integer orderId) {
//		return orderRepository.findOrderStatusByOrderId(orderId);
//	}

//	public String getCustomerName(Integer orderId) {
//		return orderRepository.getCustomerNameByOrderId(orderId);
//	}

}
