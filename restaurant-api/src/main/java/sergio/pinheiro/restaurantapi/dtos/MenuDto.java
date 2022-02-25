package sergio.pinheiro.restaurantapi.dtos;

import java.util.Calendar;

public class MenuDto {

	private String dishname;
	private boolean isAvailable;
	private Calendar week;

	public String getDishname() {
		return dishname;
	}

	public void setDishname(String dishname) {
		this.dishname = dishname;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Calendar getWeek() {
		return week;
	}

	public void setWeek(Calendar week) {
		this.week = week;
	}

}
