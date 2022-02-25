package sergio.pinheiro.restaurantapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sergio.pinheiro.restaurantapi.models.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
