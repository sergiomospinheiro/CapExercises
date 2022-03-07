package sergio.pinheiro.restaurantapi.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sergio.pinheiro.restaurantapi.converters.OrderDtoToOrder;
import sergio.pinheiro.restaurantapi.converters.OrderToOrderDto;
import sergio.pinheiro.restaurantapi.dtos.OrderDto;
import sergio.pinheiro.restaurantapi.dtos.OrderResponse;
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

	@Autowired
	private OrderToOrderDto orderToOrderDto;

	@GetMapping("/getAllOrders")
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	@PostMapping("/addOrder")
	public OrderResponse addOrder(@RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse orderResponse = new OrderResponse();

		Order order = orderDtoToOrder.convert(orderDto);

		try {
			if (!menuService.isOnSale(order)) {
				return orderResponse.sendNotOkResponse();
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		OrderResponse okResponse = orderResponse.sendOkResponse(orderDto);

		String orderHour = okResponse.getSentOn();

		Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

		order.setOrderDate(orderDate);

		order.setOrderStatus(OrderStatus.ORDER_PLACED);

		orderService.save(order);

		return okResponse;

	}

	@PostMapping("/updateOrder")
	public OrderResponse updateOrder(@RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse orderResponse = new OrderResponse();

		Order order = orderDtoToOrder.convert(orderDto);

		try {

			if (!orderService.existsById(orderDto.getOrderId())) {

				return orderResponse.sendNotOkResponse();

			}

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		order.setOrderStatus(OrderStatus.ORDER_PLACED);

		OrderResponse okResponse = orderResponse.sendOkResponse(orderDto);

		String orderHour = okResponse.getSentOn();

		Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

		order.setOrderDate(orderDate);

		orderService.save(order);

		return okResponse;

	}

	// not working properly, to review
	@DeleteMapping("/cancelOrder")
	public OrderResponse cancelOrder(@RequestBody OrderDto orderDto) {

		OrderResponse orderResponse = new OrderResponse();

		Order order = orderDtoToOrder.convert(orderDto);

		try {

			if (!orderService.existsById(orderDto.getOrderId())) {

				return orderResponse.sendNotOkResponse();

			}

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		orderService.delete(order);

		OrderDto cancelledOrderDto = orderToOrderDto.convert(order);

		return orderResponse.sendOkResponse(cancelledOrderDto);

	}

	// eventually change to post mapping //and response??
	@GetMapping("/getPurchaseStatus/{orderId}")
	public OrderStatus getPurchaseSatus(@PathVariable(value = "orderId") Integer orderId) {

		return orderService.getOrderStatus(orderId);

	}

	@PostMapping("/changePurchaseStatus")
	public Order changePurchaseStatus(@RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse orderResponse = new OrderResponse();

		OrderStatus orderStatus = orderService.getOrderStatus(orderDto.getOrderId());

		Order order = orderDtoToOrder.convert(orderDto);

		OrderResponse okResponse = orderResponse.sendOkResponse(orderDto);

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

//	// change to Post Mapping
//	@GetMapping("/getOrder/{orderId}")
//	public Order getOrder(@PathVariable(value = "orderId") Integer orderId) {
//
//		return orderService.getOrder(orderId);
//
//	}

//	@GetMapping("/getCustomerName/{orderId}")
//	public String getCustomerName(@PathVariable(value = "orderId") Integer orderId) {
//		return orderService.getCustomerName(orderId);
//	}

}
