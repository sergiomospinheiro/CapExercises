package sergio.pinheiro.restaurantapi.converters;

import org.springframework.stereotype.Component;

import sergio.pinheiro.restaurantapi.dtos.OrderDto;
import sergio.pinheiro.restaurantapi.models.Order;

@Component
public class OrderDtoToOrder {

	public Order convert(OrderDto orderDto) {
		Order order = new Order();

		order.setDishName(orderDto.getDishName());
		order.setCustomerName(orderDto.getCustomerName());
		order.setQuantity(orderDto.getQuantity());
		order.setDeliveryAddress(orderDto.getDeliveryAddress());
		order.setTransactionId(orderDto.getTransactionId());
		order.setOrderStatus(orderDto.getOrderStatus());

		return order;
	}

}
