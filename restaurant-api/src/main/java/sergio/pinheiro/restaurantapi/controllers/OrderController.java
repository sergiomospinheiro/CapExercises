package sergio.pinheiro.restaurantapi.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sergio.pinheiro.restaurantapi.converters.OrderDtoToOrder;
import sergio.pinheiro.restaurantapi.converters.OrderToOrderDto;
import sergio.pinheiro.restaurantapi.dtos.OrderDto;
import sergio.pinheiro.restaurantapi.models.Order;
import sergio.pinheiro.restaurantapi.models.OrderStatus;
import sergio.pinheiro.restaurantapi.responses.OrderResponse;
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

	@Autowired
	private OrderToOrderDto orderToOrderDto;

	@GetMapping("/getAllOrders")
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	@PostMapping("/addOrder")
	public OrderResponse addOrder(@Valid @RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse orderResponse = new OrderResponse();

		Order order = orderDtoToOrder.convert(orderDto);

		try {
			if (!menuService.isOnSale(order)) {
				return orderResponse.sendNotOkResponse(orderDto.getDishName() + " is not on sale");
			}

			else if (orderDto.getQuantity() > 10) {
				return orderResponse
						.sendNotOkResponse(orderDto.getQuantity() + " exceeds the maximum of orders possible to ask");
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		OrderResponse okResponse = orderResponse.sendOkResponse(orderDto, " added ");

		String orderHour = okResponse.getSentOn();

		Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

		order.setOrderDate(orderDate);

		order.setOrderStatus(OrderStatus.ORDER_PLACED);

		orderService.save(order);

		return okResponse;

	}

	@PostMapping("/updateOrder")
	public OrderResponse updateOrder(@Valid @RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse orderResponse = new OrderResponse();

		Order order = orderDtoToOrder.convert(orderDto);

		try {

			if (!orderService.existsById(orderDto.getOrderId())) {

				return orderResponse.sendNotOkResponse("Order not found!");

			}

			else if (!menuService.isOnSale(order)) {
				return orderResponse.sendNotOkResponse(orderDto.getDishName() + " is not on sale");
			}

			// !orderDto.getCustomerName().equals(orderService.))
			else if (!orderService.existsByCustomerName(orderDto.getCustomerName())) {
				return orderResponse.sendNotOkResponse("Customer name is not changeable");
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		order.setOrderStatus(OrderStatus.ORDER_PLACED);

		OrderResponse okResponse = orderResponse.sendOkResponse(orderDto, " updated ");

		String orderHour = okResponse.getSentOn();

		Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

		order.setOrderDate(orderDate);

		orderService.save(order);

		return okResponse;

	}

	@PostMapping("/cancelOrder")
	public OrderResponse cancelResponse(@Valid @RequestBody OrderDto orderDto) {

		OrderResponse orderResponse = new OrderResponse();

		Integer orderId = orderDto.getOrderId();

		try {

			if (!orderService.existsById(orderDto.getOrderId())) {
				return orderResponse.sendNotOkResponse("Order not found!");
			}

			else if (orderService.getOrderStatus(orderId) != OrderStatus.ORDER_PLACED) {
				return orderResponse.sendNotOkResponse("Your order is already in progress and cannot be cancelled");

			}

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		Order order = orderService.getOrder(orderId);

		orderService.deleteById(orderId);

		OrderDto cancelledOrderDto = orderToOrderDto.convert(order);

		return orderResponse.sendOkResponse(cancelledOrderDto, " cancelled ");

	}

	@PostMapping("/getPurchaseStatus")
	public OrderStatus getPurchaseStatus(@Valid @RequestBody OrderDto orderDto) {

		return orderService.getOrderStatus(orderDto.getOrderId());
	}

	@PostMapping("/changePurchaseStatus")
	public Order changePurchaseStatus(@RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse orderResponse = new OrderResponse();

		OrderStatus orderStatus = orderService.getOrderStatus(orderDto.getOrderId());

		Order order = orderDtoToOrder.convert(orderDto);

		OrderResponse okResponse = orderResponse.sendOkResponse(orderDto, " added ");

		String orderHour = okResponse.getSentOn();

		Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

		switch (orderStatus) {
		case ORDER_PLACED:
			order.setOrderStatus(OrderStatus.BEING_PREPARED);
			order.setOrderDate(orderDate);
			break;
		case BEING_PREPARED:
			order.setOrderStatus(OrderStatus.ON_THE_WAY);
			order.setOrderDate(orderDate);
			break;
		case ON_THE_WAY:
			order.setOrderStatus(OrderStatus.DELIVERED);
			order.setOrderDate(orderDate);
			break;
		default:
			System.out.println("The order has been delivered!");
			break;
		}

		return orderService.save(order);

	}

	@PostMapping("/getOrder")
	public OrderResponse getOrder(@Valid @RequestBody OrderDto orderDto) {

		OrderResponse orderResponse = new OrderResponse();

		try {

			if (!orderService.existsById(orderDto.getOrderId())) {

				return orderResponse.sendNotOkResponse("Order not found!");

			}

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		return orderResponse.sendOkResponse(orderDto, " fetched ");

	}

}
