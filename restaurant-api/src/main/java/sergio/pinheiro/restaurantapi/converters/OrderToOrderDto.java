package sergio.pinheiro.restaurantapi.converters;

import org.springframework.stereotype.Component;

import sergio.pinheiro.restaurantapi.dtos.OrderDto;
import sergio.pinheiro.restaurantapi.models.Order;

@Component
public class OrderToOrderDto {

	public OrderDto convert(Order order) {
		OrderDto orderDto = new OrderDto();

		orderDto.setDishName(order.getDishName());
		orderDto.setCustomerName(order.getCustomerName());
		orderDto.setDeliveryAddress(order.getDeliveryAddress());
		orderDto.setQuantity(order.getQuantity());
		orderDto.setOrderDate(order.getOrderDate());
		orderDto.setOrderStatus(order.getOrderStatus());

		return orderDto;
	}

}
