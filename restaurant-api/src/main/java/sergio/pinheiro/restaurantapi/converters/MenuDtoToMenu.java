package sergio.pinheiro.restaurantapi.converters;

import org.springframework.stereotype.Component;

import sergio.pinheiro.restaurantapi.dtos.MenuDto;
import sergio.pinheiro.restaurantapi.models.Menu;

@Component // to be understood
public class MenuDtoToMenu {

	public Menu convert(MenuDto menuDto) {
		Menu menu = new Menu();

		// menu.setMenuId(menuDto.getMenuId());
		menu.setDishName(menuDto.getDishName());
		menu.setWeek(menuDto.getWeek());
		menu.setAvailable(menuDto.isAvailable());
		menu.setAvailableMeals(menuDto.getAvailableMeals());
		return menu;

	}

}
