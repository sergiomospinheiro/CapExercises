package sergio.pinheiro.restaurantapi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sergio.pinheiro.restaurantapi.converters.MenuDtoToMenu;
import sergio.pinheiro.restaurantapi.dtos.MenuDto;
import sergio.pinheiro.restaurantapi.dtos.MenuResponse;
import sergio.pinheiro.restaurantapi.dtos.Response;
import sergio.pinheiro.restaurantapi.models.Menu;
import sergio.pinheiro.restaurantapi.services.MenuService;

@RestController
@RequestMapping("/api/v1/")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private MenuDtoToMenu menuDtoToMenu;

	@GetMapping("/getAll")
	public List<Menu> getAll() {
		return menuService.getAll();
	}

	@GetMapping("/getMenu")
	public List<Menu> getMenu(@RequestParam(name = "week") Integer week) {
		return menuService.getMenu(week);

	}

	@PostMapping("/addMenu")
	public Response addMenu(@Valid @RequestBody MenuDto menuDto) {
		Menu addedMenu = menuDtoToMenu.convert(menuDto);

		MenuResponse menuResponse = new MenuResponse();

		try {

			if (menuService.isOnSale(addedMenu)) {

				return menuResponse.sendNotOkResponse();

			}

			menuService.save(addedMenu);

		} catch (Exception e) {

			System.out.println("ERROR: " + e.getMessage());
		}

		return menuResponse.sendOkResponse(menuDto);

	}

	@PostMapping("/updateMenu")
	public Response updateMenu(@RequestBody @Valid MenuDto menuDto) {

		Menu updatedMenu = menuDtoToMenu.convert(menuDto);

		MenuResponse menuResponse = new MenuResponse();

		try {

			if (!menuService.existsByDishName(menuDto.getDishName())) {
				return menuResponse.sendNotOkResponse();

			}
			menuService.save(updatedMenu);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		return menuResponse.sendOkResponse(menuDto);

	}

}
