package sergio.pinheiro.restaurantapi.services;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sergio.pinheiro.restaurantapi.models.Menu;
import sergio.pinheiro.restaurantapi.models.Order;
import sergio.pinheiro.restaurantapi.repositories.MenuRepository;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;

	public List<Menu> getMenu(Integer week) {
		return menuRepository.findByWeek(week);
	}

	public List<Menu> getAll() {
		return menuRepository.findAll();
	}

	// must this be @Transactional?
	public Menu save(Menu menu) {
		return menuRepository.saveAndFlush(menu);
	}

	public boolean existsByDishName(String dishName) {
		return menuRepository.existsByDishName(dishName);
	}

	public void delete(Menu menu) {
		menuRepository.delete(menu);
	}

	public boolean isOnSale(Order order) {

		Calendar instance = Calendar.getInstance(Locale.ENGLISH);
		Integer currentWeek = instance.get(Calendar.WEEK_OF_YEAR);
		List<Menu> weekMenu = menuRepository.findByWeek(currentWeek);

		String orderDishName = order.getDishName();

		boolean isAvailable = weekMenu.stream().anyMatch(w -> w.getDishName().equals(orderDishName));

		return isAvailable;

	}

}
