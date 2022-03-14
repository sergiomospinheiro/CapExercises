package sergio.pinheiro.restaurantapi.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sergio.pinheiro.restaurantapi.dtos.OrderDto;

public class OrderResponse extends Response {

	private List<OrderDto> resValues = new ArrayList<>();

	public List<OrderDto> getResValues() {
		return resValues;
	}

	public void setResValues(List<OrderDto> resValues) {
		this.resValues = resValues;
	}

	public void addResValues(OrderDto orderDto) {
		resValues.add(orderDto);

	}

	public OrderResponse sendOkResponse(OrderDto orderDto, String message) {

		OrderResponse okResponse = new OrderResponse();
		UUID uuid = UUID.randomUUID();
		String now = now();

		okResponse.setStatusCode("200");
		okResponse.setStatus("OK");
		okResponse.setSentOn(now);
		okResponse.setTransactionID(uuid.toString());

		okResponse.addResValues(orderDto);

		okResponse.setMsg("Order" + message + "successfully!");

		return okResponse;

	}

	public OrderResponse sendNotOkResponse(String message) {
		OrderResponse notOkResponse = new OrderResponse();
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
