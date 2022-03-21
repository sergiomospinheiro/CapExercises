package sergio.pinheiro.restaurantapi.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sergio.pinheiro.restaurantapi.converters.MenuDtoToMenu;
import sergio.pinheiro.restaurantapi.dtos.MenuDto;
import sergio.pinheiro.restaurantapi.models.Menu;
import sergio.pinheiro.restaurantapi.responses.MenuResponse;
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
	public ResponseEntity<Object> addMenu(@Valid @RequestBody MenuDto menuDto) {
		Menu addedMenu = menuDtoToMenu.convert(menuDto);

		MenuResponse menuResponse = new MenuResponse();

		try {

			if (menuService.exists(addedMenu)) {

				menuResponse = menuResponse.sendNotOkResponse(menuDto.getDishName() + " already exists!");

			}

			menuService.save(addedMenu);

		} catch (Exception e) {

			System.out.println("ERROR: " + e.getMessage());
		}

		menuResponse = menuResponse.sendOkResponse(menuDto, " added ");

		return new ResponseEntity<Object>(menuResponse, HttpStatus.OK);

	}

	@PostMapping("/updateMenu")
	public ResponseEntity<Object> updateMenu(@Valid @RequestBody MenuDto menuDto) {

		MenuResponse menuResponse = new MenuResponse();

		Menu menu = new Menu();

		boolean flagCheck = true;

		try {

			if (menuDto.getDishName() == null) {
				menuResponse = menuResponse.sendNotOkResponse("Missing dish name!");
				flagCheck = false;
			} else {

				Optional<Menu> getDish = Optional.of(menuService.getDish(menuDto.getDishName()));

				if (!getDish.isPresent()) {
					menuResponse = menuResponse.sendNotOkResponse(menuDto.getDishName() + " does not exist");
					flagCheck = false;
				} else {
					menu.setMenuId(getDish.get().getMenuId());
					menu.setDishName(getDish.get().getDishName());
					menu.setAvailable(getDish.get().isAvailable());

					if (menuDto.getAvailableMeals() == null) {
						menu.setAvailableMeals(getDish.get().getAvailableMeals());
						flagCheck = true;
					} else {
						menu.setAvailableMeals(menuDto.getAvailableMeals());
						flagCheck = true;
					}
					if (menuDto.isAvailable() == null) {
						menu.setAvailable(getDish.get().isAvailable());
						flagCheck = true;
					} else {
						menu.setAvailable(menuDto.isAvailable());
						flagCheck = true;
					}
					if (menuDto.getWeek() != null) {
						menu.setWeek(menuDto.getWeek());
						flagCheck = true;
					} else {
						menu.setWeek(getDish.get().getWeek());
						flagCheck = true;
					}

				}

			}
		}

		catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		if (flagCheck) {
			menuResponse = menuResponse.sendOkResponse(menuDto, " updated ");

			menuService.save(menu);
		}

		return new ResponseEntity<Object>(menuResponse, HttpStatus.OK);

	}

}
