package sergio.pinheiro.restaurantapi.dtos;

import java.util.ArrayList;
import java.util.List;

public class OrderResponse extends Response {

	private List<OrderDto> resValues = new ArrayList<>();

	public List<OrderDto> getResValues() {
		return resValues;
	}

	public void setResValues(List<OrderDto> resValues) {
		this.resValues = resValues;
	}

}

//parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
