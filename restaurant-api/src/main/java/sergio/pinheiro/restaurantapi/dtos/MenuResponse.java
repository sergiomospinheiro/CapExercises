package sergio.pinheiro.restaurantapi.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
		okResponse.setTransactionID(uuid.toString());
		okResponse.setMsg("Menu added successfully!");

		okResponse.addResValues(menuDto);

		return okResponse;

	}

	public MenuResponse sendNotOkResponse() {
		MenuResponse notOkResponse = new MenuResponse();
		UUID uuid = UUID.randomUUID();
		String now = now();

		notOkResponse.setStatusCode("500");
		notOkResponse.setStatus("NOK");
		notOkResponse.setSentOn(now);
		notOkResponse.setTransactionID(uuid.toString());
		notOkResponse.setMsg("Error: Fetch the error");

		return notOkResponse;
	}

}
