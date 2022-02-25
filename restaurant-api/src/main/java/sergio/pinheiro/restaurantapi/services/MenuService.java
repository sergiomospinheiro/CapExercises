package sergio.pinheiro.restaurantapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sergio.pinheiro.restaurantapi.models.Menu;
import sergio.pinheiro.restaurantapi.repositories.MenuRepository;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;

	public List<Menu> getMenu() {

		return menuRepository.findAll();
	}

//	public List<Menu> getMenuByAvailable() {
//		return menuRepository.
//	}

	public Menu save(Menu menu) {
		return menuRepository.saveAndFlush(menu);
	}

	public void delete(Menu menu) {
		menuRepository.delete(menu);
	}

}
