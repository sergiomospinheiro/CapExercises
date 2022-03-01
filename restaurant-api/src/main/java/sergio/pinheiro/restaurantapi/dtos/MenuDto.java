package sergio.pinheiro.restaurantapi.dtos;

public class MenuDto {

	private String dishName;
	// private boolean isAvailable;
	private Integer week;

	public String getDishName() {
		return dishName;
	}

	public void setDishname(String dishName) {
		this.dishName = dishName;
	}

	// public boolean isAvailable() {
	// return isAvailable;

	// }

	// public void setAvailable(boolean isAvailable) {
	// this.isAvailable=isAvailable;}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

}
