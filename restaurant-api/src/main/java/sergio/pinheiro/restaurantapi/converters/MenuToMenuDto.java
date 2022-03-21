package sergio.pinheiro.restaurantapi.converters;

import org.springframework.stereotype.Component;

import sergio.pinheiro.restaurantapi.dtos.MenuDto;
import sergio.pinheiro.restaurantapi.models.Menu;

@Component
public class MenuToMenuDto {

	public MenuDto convert(Menu menu) {
		MenuDto menuDto = new MenuDto();

		// menuDto.setMenuId(menu.getMenuId());
		menuDto.setDishname(menu.getDishName());
		menuDto.setAvailable(menu.isAvailable());
		menuDto.setWeek(menu.getWeek());
		menuDto.setAvailableMeals(menu.getAvailableMeals());
		return menuDto;

	}

}
