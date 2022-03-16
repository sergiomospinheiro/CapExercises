package sergio.pinheiro.restaurantapi.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
				return orderResponse.sendNotOkResponse(orderDto.getQuantity() + " exceeds the limit of orders");
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
	public ResponseEntity<OrderResponse> updateOrder(@Valid @RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse orderResponse = new OrderResponse();

		Order order = orderDtoToOrder.convert(orderDto);
		Optional<Order> getOrder = Optional.of(orderService.getOrder(orderDto.getOrderId()));
		Order testOrder = new Order();
		boolean flagCheck = true;

		try {

			if (!getOrder.isPresent()) {
				orderResponse = orderResponse.sendNotOkResponse("Order not found!");
				flagCheck = false;
			} else {
				testOrder.setDishName(getOrder.get().getDishName());
				testOrder.setOrderId(getOrder.get().getOrderId());
				testOrder.setDeliveryAddress(getOrder.get().getDeliveryAddress());
				testOrder.setCustomerName(getOrder.get().getCustomerName());

				if (!menuService.isOnSale(testOrder)) {
					orderResponse = orderResponse.sendNotOkResponse(orderDto.getDishName() + " is not on sale");
					flagCheck = false;
				}

				else if (!testOrder.getCustomerName().equals(orderDto.getCustomerName())) {
					orderResponse = orderResponse.sendNotOkResponse("Customer name is not changeable");
					flagCheck = false;
				}

				else if (orderDto.getQuantity() > 10) {
					orderResponse = orderResponse
							.sendNotOkResponse(orderDto.getQuantity() + " exceeds the limit of orders");
					flagCheck = false;
				}

				else if (testOrder.getOrderStatus() != OrderStatus.ORDER_PLACED) {
					orderResponse.sendNotOkResponse("We are sorry but the order is already in progress");
					flagCheck = false;
				}
			}

		} catch (Exception e) {
			flagCheck = false;
			System.out.println("ERROR: " + e.getMessage());
			orderResponse = orderResponse.sendNotOkResponse("ERROR: " + e.getMessage());
		}

		if (flagCheck) {

			order.setOrderStatus(OrderStatus.ORDER_PLACED);

			orderResponse = orderResponse.sendOkResponse(orderDto, " updated ");

			String orderHour = orderResponse.getSentOn();

			Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

			order.setOrderDate(orderDate);

			orderService.save(order);
		}

		return new ResponseEntity<>(orderResponse, HttpStatus.OK);

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
