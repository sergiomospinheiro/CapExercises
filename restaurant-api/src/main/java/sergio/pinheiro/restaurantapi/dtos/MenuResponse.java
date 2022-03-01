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

	public Response sendOkResponse() {
		Response okResponse = new MenuResponse();
		UUID uuid = UUID.randomUUID();
		setStatusCode("200");
		setStatus("OK");
		setSentOn("now");
		setTransactionID(uuid.toString());
		setMsg("Menu added successfully!");

		// setResValues(this.addResValues(menuDto));

		return okResponse;

	}

	public Response sendNotOkResponse() {
		Response notOkResponse = new MenuResponse();
		UUID uuid = UUID.randomUUID();
		setStatusCode("500");
		setStatus("NOK");
		setSentOn("now");
		setTransactionID(uuid.toString());
		setMsg("Error: Fetch the error");

		return notOkResponse;
	}

}
