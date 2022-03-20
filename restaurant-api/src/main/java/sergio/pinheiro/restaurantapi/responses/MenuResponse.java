package sergio.pinheiro.restaurantapi.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sergio.pinheiro.restaurantapi.dtos.MenuDto;

public class MenuResponse extends Response {

	private List<MenuDto> resValues = new ArrayList<>();

	public List<MenuDto> getResValues() {
		return resValues;
	}

	public void setResValues(List<MenuDto> resValues) {
		this.resValues = resValues;
	}

	public void addResValues(MenuDto menuDto) {
		resValues.add(menuDto);
	}

	public MenuResponse sendOkResponse(MenuDto menuDto) {
		MenuResponse okResponse = new MenuResponse();
		UUID uuid = UUID.randomUUID();
		String now = now();

		okResponse.setStatusCode("200");
		okResponse.setStatus("OK");
		okResponse.setSentOn(now);
		okResponse.setTransactionId(uuid.toString());
		okResponse.setMsg("Menu added successfully!");

		okResponse.addResValues(menuDto);

		return okResponse;

	}

	public MenuResponse sendNotOkResponse(String message) {
		MenuResponse notOkResponse = new MenuResponse();
		UUID uuid = UUID.randomUUID();
		String now = now();

		notOkResponse.setStatusCode("500");
		notOkResponse.setStatus("NOK");
		notOkResponse.setSentOn(now);
		notOkResponse.setTransactionId(uuid.toString());
		notOkResponse.setMsg("Error: " + message);

		return notOkResponse;
	}

}
