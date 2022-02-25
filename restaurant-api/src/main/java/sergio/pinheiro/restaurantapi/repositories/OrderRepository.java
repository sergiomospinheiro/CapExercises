package sergio.pinheiro.restaurantapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sergio.pinheiro.restaurantapi.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
