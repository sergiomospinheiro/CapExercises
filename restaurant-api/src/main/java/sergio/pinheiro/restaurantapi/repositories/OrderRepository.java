package sergio.pinheiro.restaurantapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sergio.pinheiro.restaurantapi.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	boolean existsByCustomerName(String customerName);

	Optional<Order> findById(Integer orderId);
//	OrderStatus findOrderStatusByOrderId(Integer orderId);
//
//	String findCustomerNameByOrderId(Integer orderId);
//
//	String getCustomerNameByOrderId(Integer orderId);

}