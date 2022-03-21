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
import sergio.pinheiro.restaurantapi.models.Menu;
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
	private Menu menu;

	@Autowired
	private MenuService menuService;

	@Autowired
	private OrderDtoToOrder orderDtoToOrder;

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

		Integer dishesAv = menuService.getDishesQuantity(orderDto.getDishName());

		try {
			if (!menuService.isOnSale(order)) {
				log.error("This dish is not on sale");

				return orderResponse.sendNotOkResponse(orderDto.getDishName() + " is not on sale");
			}

			else if (menuService.isQuantityAvailable(dishesAv, orderDto.getQuantity()) == true) {
				return orderResponse.sendNotOkResponse(orderDto.getQuantity() + " is not possible to order");
			}

			// checking if there are enough dishes

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
			String orderHour = sdf.format(cal.getTime());

			Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

			order.setOrderDate(orderDate);
			order.setOrderDateEnd(orderDate);
			order.setTransactionId(transactID);

			order.setOrderStatus(OrderStatus.ORDER_PLACED);

			orderService.save(order);

			okResponse = orderResponse.sendOkResponse(order, " added ");
			okResponse.setTransactionId(transactID);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		return okResponse;

	}

	@PostMapping("/updateOrder")
	public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse<OrderDto> orderResponse = new OrderResponse<OrderDto>();

		Order order = new Order();

		OrderStatus orderStatusDto = orderDto.getOrderStatus(); // this can be null at this point

		// this is null at this point // can I replace this for
		// getOrder.getOrderStatus?

		boolean flagCheck = true;

		try {

			// validate if we have the OrderID // I've changed from getting orderID to
			// transactionId
			if (orderDto.getTransactionId() == null) {
				orderResponse = orderResponse.sendNotOkResponse("Missing transactionId!");
				flagCheck = false;
			} else {
				// to make a validation of the fetched order

				Optional<Order> getOrder = Optional.of(orderService.getOrder(orderDto.getTransactionId()));
				OrderStatus orderStatusModel = getOrder.get().getOrderStatus(); // make sure that the second validation
																				// gets this \\ question of scope

				if (!getOrder.isPresent() || !orderService.isOrderStatusValid(orderStatusModel)) {
					orderResponse = orderResponse
							.sendNotOkResponse("Order not found! or Order Status not possible to change"); // optimize
																											// this //
																											// the
																											// method is
																											// also
																											// sending a
																											// message
					flagCheck = false;
				} else {
					order.setDishName(getOrder.get().getDishName()); // check
					order.setOrderId(getOrder.get().getOrderId());
					order.setCustomerName(getOrder.get().getCustomerName());
					order.setQuantity(getOrder.get().getQuantity()); // check
					order.setOrderStatus(getOrder.get().getOrderStatus());
					order.setTransactionId(getOrder.get().getTransactionId());

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
						orderResponse.setTransactionId(order.getTransactionId());
						orderResponse = orderResponse
								.sendNotOkResponse(orderDto.getQuantity() + " not possible to insert");
						flagCheck = false;
					} else {
						order.setQuantity(orderDto.getQuantity());
						flagCheck = true;

					}

					// validating order status

//					OrderStatus orderStatusDto = orderDto.getOrderStatus();
//
//					OrderStatus orderStatusModel = order.getOrderStatus();

					if (orderStatusDto != null && orderService.isOrderStatusValid(orderStatusModel)
							&& orderService.changeOrderStatus(orderStatusDto, orderStatusModel)) {

						order.setOrderStatus(orderStatusDto);
						flagCheck = true;
					} else {
						orderResponse.setTransactionId(order.getTransactionId());
						orderResponse = orderResponse.sendNotOkResponse("Order can't be changed");
						flagCheck = false;
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
			orderResponse.setTransactionId(order.getTransactionId());

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
	public ResponseEntity<?> getOrder(@RequestBody OrderDto orderDto) { // I have withdrawn the annotation @Valid

		OrderResponse<OrderDto> orderResponse = new OrderResponse<OrderDto>();

		try {
			Optional<Order> getOrder = orderRepository.findByTransactionId(orderDto.getTransactionId());
			if (!getOrder.isPresent()) {
				orderResponse = orderResponse.sendNotOkResponse("Order not found!");
			} else {

				OrderToOrderDto orderToOrderDto = new OrderToOrderDto();
				Order tmpOrder = new Order();

				tmpOrder.setDishName(getOrder.get().getDishName());
				tmpOrder.setCustomerName(getOrder.get().getCustomerName());
				tmpOrder.setDeliveryAddress(getOrder.get().getDeliveryAddress());
				tmpOrder.setQuantity(getOrder.get().getQuantity());
				tmpOrder.setTransactionId(getOrder.get().getTransactionId());
				tmpOrder.setOrderStatus(getOrder.get().getOrderStatus());

				OrderDto convertOrder = orderToOrderDto.convert(tmpOrder);

				orderResponse.addResValues(convertOrder);
				orderResponse.setStatus("OK");
				orderResponse.setMsg("Valores retornados");
//				orderResponse.setTransactionId();
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
