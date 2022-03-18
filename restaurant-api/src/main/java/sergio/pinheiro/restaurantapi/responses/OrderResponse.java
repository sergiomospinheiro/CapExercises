package sergio.pinheiro.restaurantapi.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderResponse<T> extends Response {

	private List<T> resValues = new ArrayList<>();

	public List<T> getResValues() {
		return resValues;
	}

	public void setResValues(List<T> resValues) {
		this.resValues = resValues;
	}

	public void addResValues(T orderDto) {
		resValues.add(orderDto);

	}

	public OrderResponse<T> sendOkResponse(T orderDto, String message) {

		OrderResponse<T> okResponse = new OrderResponse<T>();
		// UUID uuid = UUID.randomUUID();
		String now = now();

		okResponse.setStatusCode("200");
		okResponse.setStatus("OK");
		okResponse.setSentOn(now);
		// okResponse.setTransactionID(uuid.toString());

		okResponse.addResValues(orderDto);

		okResponse.setMsg("Order" + message + "successfully!");

		return okResponse;

	}

	public OrderResponse<T> sendNotOkResponse(String message) {
		OrderResponse<T> notOkResponse = new OrderResponse<T>();
		UUID uuid = UUID.randomUUID();
		String now = now();

		notOkResponse.setStatusCode("500");
		notOkResponse.setStatus("NOK");
		notOkResponse.setSentOn(now);
		notOkResponse.setTransactionID(uuid.toString());
		notOkResponse.setMsg("Error: " + message);

		return notOkResponse;
	}

}
