package sergio.pinheiro.restaurantapi.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import sergio.pinheiro.restaurantapi.repositories.OrderRepository;
import sergio.pinheiro.restaurantapi.responses.OrderResponse;
import sergio.pinheiro.restaurantapi.services.MenuService;
import sergio.pinheiro.restaurantapi.services.OrderService;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd: HH:mm:ss";

	Logger log = LogManager.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private OrderDtoToOrder orderDtoToOrder;

	// @Autowired
	// private OrderToOrderDto orderToOrderDto;

	@Autowired
	private OrderRepository orderRepository;

	@GetMapping("/getAllOrders")
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	@PostMapping("/addOrder")
	public OrderResponse<Order> addOrder(@Valid @RequestBody OrderDto orderDto) throws ParseException {

		log.info("Estou no add: " + orderDto.toString());

		OrderResponse<Order> orderResponse = new OrderResponse<Order>();
		OrderResponse<Order> okResponse = new OrderResponse<Order>();

		String transactID = UUID.randomUUID().toString();

		Order order = orderDtoToOrder.convert(orderDto);

		try {
			if (!menuService.isOnSale(order)) {
				log.error("This dish is not on sale");

				return orderResponse.sendNotOkResponse(orderDto.getDishName() + " is not on sale");
			}

			else if (orderDto.getQuantity() > 10) {
				return orderResponse.sendNotOkResponse(orderDto.getQuantity() + " exceeds the limit of orders");
			}

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
			String orderHour = sdf.format(cal.getTime());

			Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

			order.setOrderDate(orderDate);
			order.setOrderDateEnd(orderDate);
			order.setTransactionID(transactID);

			order.setOrderStatus(OrderStatus.ORDER_PLACED);

			orderService.save(order);

			okResponse = orderResponse.sendOkResponse(order, " added ");
			okResponse.setTransactionID(transactID);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		return okResponse;

	}

	@PostMapping("/updateOrder")
	public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse<OrderDto> orderResponse = new OrderResponse<OrderDto>();

		Order order = new Order();

		boolean flagCheck = true;

		try {

			// validate if we have the OrderID
			if (orderDto.getOrderId() == null) {
				orderResponse = orderResponse.sendNotOkResponse("Missing the ID!");
				flagCheck = false;
			} else {
				// to make a validation of the fetched order
				Optional<Order> getOrder = Optional.of(orderService.getOrder(orderDto.getOrderId()));

				if (!getOrder.isPresent()) // it's throwing a no such element exception and not the response
				{
					orderResponse = orderResponse.sendNotOkResponse("Order not found!");
					flagCheck = false;
				} else {
					order.setDishName(getOrder.get().getDishName()); // check
					order.setOrderId(getOrder.get().getOrderId());
					order.setCustomerName(getOrder.get().getCustomerName());
					order.setQuantity(getOrder.get().getQuantity()); // check
					order.setOrderStatus(getOrder.get().getOrderStatus());
					order.setTransactionID(getOrder.get().getTransactionID());

					if (orderDto.getDishName() == null && !order.getDishName().equals(orderDto.getDishName())) {

						log.warn("User is trying to change the dish");

					}

					if (orderDto.getDeliveryAddress() != null
							&& !getOrder.get().getDeliveryAddress().equals(orderDto.getDeliveryAddress())) {

						order.setDeliveryAddress(orderDto.getDeliveryAddress());
						flagCheck = true;
					} else {
						order.setDeliveryAddress(getOrder.get().getDeliveryAddress());
					}

					// não existe uma stream que encadeia?
					if (!order.getOrderStatus().equals(OrderStatus.ORDER_PLACED)
							&& !order.getOrderStatus().equals(OrderStatus.BEING_PREPARED) || orderDto.getQuantity() == 0
							|| orderDto.getQuantity() > 30) {
						orderResponse = orderResponse
								.sendNotOkResponse(orderDto.getQuantity() + " not possible to insert");
						flagCheck = false;
					} else {
						order.setQuantity(orderDto.getQuantity());
						flagCheck = true;

					}

					if (orderDto.getOrderStatus().equals(null)) {
						flagCheck = false;
					} else {
						orderService.changeOrderStatus(orderDto.getOrderStatus());
					}

				}

			}

		} catch (Exception e) {
			flagCheck = false;
			System.out.println("ERROR: " + e.getMessage());
			orderResponse = orderResponse.sendNotOkResponse("ERROR: " + e.getMessage());
		}

		if (flagCheck) {

			orderResponse = orderResponse.sendOkResponse(orderDto, " updated ");
			orderResponse.setTransactionID(order.getTransactionID());

			String orderHour = orderResponse.getSentOn();

			Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

			order.setOrderDate(orderDate); // maybe doesn't make sense because the orderDate should be the original
											// order date

			order.setOrderDateEnd(orderDate);

			orderService.save(order);
		}

		return new ResponseEntity<>(orderResponse, HttpStatus.OK);

	}

	@PostMapping("/getOrder")
	public ResponseEntity<?> getOrder(@Valid @RequestBody OrderDto orderDto) {

		OrderResponse<OrderDto> orderResponse = new OrderResponse<OrderDto>();

		try {
			Optional<Order> getOrder = orderRepository.findById(orderDto.getOrderId());
			if (!getOrder.isPresent()) {
				orderResponse = orderResponse.sendNotOkResponse("Order not found!");
			} else {

				OrderToOrderDto orderToOrderDto = new OrderToOrderDto();
				Order tmpOrder = new Order();
				tmpOrder.setDishName(getOrder.get().getDishName());
				tmpOrder.setCustomerName(getOrder.get().getCustomerName());
				tmpOrder.setDeliveryAddress(getOrder.get().getDeliveryAddress());
				tmpOrder.setQuantity(getOrder.get().getQuantity());
				OrderDto convertOrder = orderToOrderDto.convert(tmpOrder);
				orderResponse.addResValues(convertOrder);
				orderResponse.setStatusCode("OK");
				orderResponse.setMsg("Valores retornados");
				// orderResponse.setTransactionID();
				// setdate
			}

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			orderResponse.setStatus("NOK");
			orderResponse.setMsg("ERROR: " + e.getMessage());
		}

		return new ResponseEntity<>(orderResponse, HttpStatus.OK);

	}

}
