package sergio.pinheiro.restaurantapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sergio.pinheiro.restaurantapi.converters.OrderDtoToOrder;
import sergio.pinheiro.restaurantapi.dtos.OrderDto;
import sergio.pinheiro.restaurantapi.dtos.OrderResponse;
import sergio.pinheiro.restaurantapi.models.Order;
import sergio.pinheiro.restaurantapi.services.OrderService;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDtoToOrder orderDtoToOrder;

	@PostMapping("/addOrder")
	public OrderResponse addOrder(@RequestBody OrderDto orderDto) {

		Order order = orderDtoToOrder.convert(orderDto);

		OrderResponse okResponse = new OrderResponse();

		try {

			orderService.save(order);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		return okResponse;

	}

}
