package sergio.pinheiro.restaurantapi.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MenuDto {

	private Integer menuId;

	@NotBlank
	@Pattern(regexp = "^[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ\\s]+$")
	private String dishName;
	@Min(value = 1, message = "minimum value is 1, first week of the year")
	@Max(value = 52, message = "maximum value is 52, last week of the year")
	private Integer week;

	private Boolean available;

	@Min(value = 1, message = "minimum value is 1 meal")
	@Max(value = 30, message = "maximum value is 30")
	private Integer availableMeals;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public void setDishname(String dishName) {
		this.dishName = dishName;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Boolean isAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Integer getAvailableMeals() {
		return availableMeals;
	}

	public void setAvailableMeals(Integer availableMeals) {
		this.availableMeals = availableMeals;
	}

}
