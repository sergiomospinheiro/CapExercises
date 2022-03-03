package sergio.pinheiro.restaurantapi.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sergio.pinheiro.restaurantapi.converters.OrderDtoToOrder;
import sergio.pinheiro.restaurantapi.dtos.OrderDto;
import sergio.pinheiro.restaurantapi.dtos.OrderResponse;
import sergio.pinheiro.restaurantapi.models.Menu;
import sergio.pinheiro.restaurantapi.models.Order;
import sergio.pinheiro.restaurantapi.models.OrderStatus;
import sergio.pinheiro.restaurantapi.services.MenuService;
import sergio.pinheiro.restaurantapi.services.OrderService;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private OrderDtoToOrder orderDtoToOrder;

	@GetMapping("/getAllOrders")
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	public boolean containsName(List<Menu> weekMenu, String dishName) {
		return weekMenu.stream().filter(dto -> dto.getDishName().equals(dishName)).findFirst().isPresent();
	}

	@PostMapping("/addOrder")
	public OrderResponse addOrder(@RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse orderResponse = new OrderResponse();

		Calendar instance = Calendar.getInstance(Locale.ENGLISH);
		Integer currentWeek = instance.get(Calendar.WEEK_OF_YEAR);
		List<Menu> weekMenu = menuService.getMenu(currentWeek);

		String orderDishName = orderDto.getDishName();

		boolean isAvailable = weekMenu.stream().map(w -> w.getDishName()).equals(orderDishName);

		try {
			if (!isAvailable) {
				return orderResponse.sendNotOkResponse(orderDto);
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		Order order = orderDtoToOrder.convert(orderDto);

		OrderResponse okResponse = orderResponse.sendOkResponse(orderDto);

		String orderHour = okResponse.getSentOn();

		Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

		order.setOrderDate(orderDate);

		order.setOrderStatus(OrderStatus.ORDER_PLACED);

		orderService.save(order);

		return okResponse;

	}

}
