package sergio.pinheiro.restaurantapi.dtos;

import java.util.ArrayList;
import java.util.List;

public class MenuResponse extends Response {

	private List<MenuDto> resValues = new ArrayList<>();

	public List<MenuDto> getResValues() {
		return resValues;
	}

	public void setResValues(List<MenuDto> resValues) {
		this.resValues = resValues;
	}

}
