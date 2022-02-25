package sergio.pinheiro.restaurantapi.converters;

import org.springframework.stereotype.Component;

import sergio.pinheiro.restaurantapi.dtos.MenuDto;
import sergio.pinheiro.restaurantapi.models.Menu;

@Component
public class MenuDtoToMenu {

	public Menu convert(MenuDto menuDto) {
		Menu menu = new Menu();

		menu.setDishName(menuDto.getDishname());
		menu.setAvailable(menuDto.isAvailable());
		menu.setWeek(menuDto.getWeek());
		return menu;

	}

}
