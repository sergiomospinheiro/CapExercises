package sergio.pinheiro.restaurantapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sergio.pinheiro.restaurantapi.converters.MenuDtoToMenu;
import sergio.pinheiro.restaurantapi.dtos.MenuDto;
import sergio.pinheiro.restaurantapi.dtos.MenuResponse;
import sergio.pinheiro.restaurantapi.models.Menu;
import sergio.pinheiro.restaurantapi.services.MenuService;

@RestController
@RequestMapping("/api/v1/")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@GetMapping("/getMenu")
	public List<Menu> getMenu(@RequestBody MenuDto menuDto) {

		MenuDtoToMenu menuDtoToMenu = new MenuDtoToMenu();

		Menu menu = menuDtoToMenu.convert(menuDto);

		if (menu.isAvailable()) {

		}
		return menuService.getMenu();
	}

	@PostMapping("/addMenu")
	public MenuResponse addMenu(@RequestBody MenuDto menuDto) {

		Menu menu = new Menu();

		menuService.save(menu);

		MenuResponse menuResponse = new MenuResponse();

		return menuResponse;

//		
//		try {
//			if(menuService.existsBy)
//		}

	}

}
