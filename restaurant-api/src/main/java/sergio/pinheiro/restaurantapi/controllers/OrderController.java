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
	public ResponseEntity<?> addOrder(@Valid @RequestBody OrderDto orderDto) throws ParseException {

		log.info("Estou no add: " + orderDto.toString());

		OrderResponse<Order> orderResponse = new OrderResponse<Order>();
		OrderResponse<Order> okResponse = new OrderResponse<Order>();

		String transactID = UUID.randomUUID().toString();

		Order order = orderDtoToOrder.convert(orderDto);

		Menu menu = new Menu();

		int availableMeals = 0;

		// Integer dishesAv = menuService.getDishesQuantity(orderDto.getDishName());

		try {
			if (!menuService.isOnSale(order)) {
				log.error("This dish is not on sale");

				orderResponse = orderResponse.sendNotOkResponse(orderDto.getDishName() + " is not on sale");
			} else {

				Optional<Menu> getMenu = Optional.of(menuService.getDish(orderDto.getDishName()));

				availableMeals = getMenu.get().getAvailableMeals();

				// tirei os sets de dois campos e espetei logo o objecto, medir consequências
				menu = getMenu.get();
			}

			if (menuService.checkQuantityAvailable(availableMeals, orderDto.getQuantity()) == false) {
				orderResponse = orderResponse.sendNotOkResponse("Article not available");
			} else {
				log.warn("Isto está a bombar!");
				order.setQuantity(orderDto.getQuantity());

				menu.setAvailableMeals(availableMeals - orderDto.getQuantity());

			}

			menuService.save(menu);

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

		return new ResponseEntity<>(okResponse, HttpStatus.OK);

	}

	@PostMapping("/updateOrder")
	public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto) throws ParseException {

		OrderResponse<OrderDto> orderResponse = new OrderResponse<OrderDto>();

		Order order = new Order();

		Menu menu = new Menu();

		OrderStatus orderStatusDto = orderDto.getOrderStatus();

		boolean flagCheck = true;
		boolean flagMenu = false;
		boolean flagCancel = false;

		try {

			if (orderDto.getTransactionId() == null) {
				orderResponse = orderResponse.sendNotOkResponse("Missing transactionId!");
				flagCheck = false;
			} else {

				// to make the validation of the fetched order

				Optional<Order> getOrder = Optional.of(orderService.getOrder(orderDto.getTransactionId()));
				OrderStatus orderStatusModel = getOrder.get().getOrderStatus();

				if (!getOrder.isPresent() || !orderService.isOrderStatusValid(orderStatusModel)) {
					orderResponse = orderResponse
							.sendNotOkResponse("Order not found! or Order Status not possible to change");

					flagCheck = false;
				} else {

					String dishName = getOrder.get().getDishName();

					Optional<Menu> getMenu = Optional.of(menuService.getDish(dishName));

					menu = getMenu.get();

					order.setDishName(getOrder.get().getDishName());
					order.setOrderId(getOrder.get().getOrderId());
					order.setCustomerName(getOrder.get().getCustomerName());
					order.setQuantity(getOrder.get().getQuantity());
					order.setOrderStatus(getOrder.get().getOrderStatus());
					order.setTransactionId(getOrder.get().getTransactionId());
					order.setOrderDate(getOrder.get().getOrderDate());

					// validating delivery address

					if (orderDto.getDeliveryAddress() != null
							&& !getOrder.get().getDeliveryAddress().equals(orderDto.getDeliveryAddress())) {

						order.setDeliveryAddress(orderDto.getDeliveryAddress());
						flagCheck = true;
					} else {
						order.setDeliveryAddress(getOrder.get().getDeliveryAddress());
					}

					if (orderDto.getQuantity() != 0) {

						int updatedQtDifference = orderDto.getQuantity() - order.getQuantity();

						int availableMeals = getMenu.get().getAvailableMeals();

						boolean statusCount = menuService.checkQuantityAvailable(availableMeals, updatedQtDifference);

						if (statusCount) {
							menu.setAvailableMeals(availableMeals - updatedQtDifference);
							order.setQuantity(orderDto.getQuantity());
							flagMenu = true;
							flagCheck = true;
						}

					}

					if (orderStatusDto != null && orderService.isOrderStatusValid(orderStatusModel)
							&& orderService.changeOrderStatus(orderStatusDto, orderStatusModel)) {

						if (orderStatusDto == OrderStatus.ORDER_CANCELLED &&
							 getOrder.get().getOrderStatus() != OrderStatus.ORDER_CANCELLED) {

							int availableMenuMeals = getMenu.get().getAvailableMeals();
							menu.setAvailableMeals(availableMenuMeals + order.getQuantity());
							flagCheck = true;
						} else {
							orderResponse = orderResponse
							.sendNotOkResponse("Order is Already Canclled!!");
						}

						order.setOrderStatus(orderStatusDto);
						flagCheck = true;
					}
				}

			}
			// if you have conditions to update values
			if (flagCheck) {

				orderResponse = orderResponse.sendOkResponse(orderDto, " updated ");
				orderResponse.setTransactionId(order.getTransactionId());

				String orderHour = orderResponse.getSentOn();

				Date orderDate = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss").parse(orderHour);

				order.setOrderDateEnd(orderDate);

				orderService.save(order);

				if (flagCancel) {
					menuService.save(menu);
				}

				// update menu
				if (flagMenu)
					menuService.save(menu);

			}

		} catch (Exception e) {
			flagCheck = false;
			System.out.println("ERROR: " + e.getMessage());
			orderResponse = orderResponse.sendNotOkResponse("ERROR: " + e.getMessage());

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
