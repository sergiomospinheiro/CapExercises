package sergio.pinheiro.restaurantapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sergio.pinheiro.restaurantapi.models.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

	Optional<Menu> findByDishName(String dishName);

	@Query(value = "SELECT * FROM menu_spi WHERE dish_name = :dishName AND availability = true", nativeQuery = true)
	Optional<Menu> findByActiveDish(String dishName);

	Optional<Menu> findByMenuId(Integer menuId);

	List<Menu> findByWeek(Integer week);

	boolean existsByDishName(String dishName);

}
