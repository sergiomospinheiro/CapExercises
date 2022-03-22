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

	public Menu getDish(String dishName) {
		return menuRepository.findByActiveDish(dishName).get();
	}

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

	public boolean isOnSale(Order getOrder) {
		boolean isOnSale = false;
		Calendar instance = Calendar.getInstance(Locale.ENGLISH);
		Integer currentWeek = instance.get(Calendar.WEEK_OF_YEAR);
		List<Menu> weekMenu = menuRepository.findByWeek(currentWeek);

		String orderDishName = getOrder.getDishName();

		isOnSale = weekMenu.stream().anyMatch(w -> w.getDishName().equals(orderDishName));

		return isOnSale;

	}

	public boolean exists(Menu menu) {

		List<Menu> menuList = menuRepository.findAll();

		String menuDishName = menu.getDishName();

		boolean exists = menuList.stream().anyMatch(w -> w.getDishName().equals(menuDishName));

		return exists;

	}

	public boolean checkQuantityAvailable(int dishAvailableQt, int dishQtOrder) {
		boolean available = true;
		if (dishAvailableQt < dishQtOrder) {
			available = false;
		}

		return available;

	}

	// to delete probably
	public Integer getDishesQuantity(String dishName) {
		Integer qtDishesAvailable = menuRepository.findByDishName(dishName).get().getAvailableMeals();
		return qtDishesAvailable;

	}

}
