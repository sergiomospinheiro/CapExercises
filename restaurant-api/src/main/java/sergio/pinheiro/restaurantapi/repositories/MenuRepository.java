package sergio.pinheiro.restaurantapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sergio.pinheiro.restaurantapi.models.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

	List<Menu> findByWeek(Integer week);

	boolean existsByDishName(String dishName);

}
