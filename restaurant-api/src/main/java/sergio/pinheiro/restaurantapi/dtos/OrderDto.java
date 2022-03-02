package sergio.pinheiro.restaurantapi.dtos;

import java.util.Calendar;
import java.util.Locale;

public class OrderDto {

	private String dishName;
	private String customerName;
	private int quantity;
	private String deliveryAddress;

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

	public Integer getWeekYear() {
		Calendar instance = Calendar.getInstance(Locale.ENGLISH);
		Integer week = instance.get(Calendar.WEEK_OF_YEAR);
		return week;
	}

}
