package sergio.pinheiro.restaurantapi.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import sergio.pinheiro.restaurantapi.models.OrderStatus;

public class OrderDto {

	private Integer orderId;

	@NotEmpty
	@Pattern(regexp = "^[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ\\s]+$")
	private String dishName;
	@Pattern(regexp = "^[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ\\s]+$")
	private String customerName;
	@NotNull
	@Positive
	private int quantity;
	@NotEmpty
	private String deliveryAddress;

	private OrderStatus orderStatus;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "OrderDto [orderId=" + orderId + ", dishName=" + dishName + ", customerName=" + customerName
				+ ", quantity=" + quantity + ", deliveryAddress=" + deliveryAddress + ", orderStatus=" + orderStatus
				+ "]";
	}

//	public Integer getWeekYear() {
//		Calendar instance = Calendar.getInstance(Locale.ENGLISH);
//		Integer week = instance.get(Calendar.WEEK_OF_YEAR);
//		return week;
//	}

}
