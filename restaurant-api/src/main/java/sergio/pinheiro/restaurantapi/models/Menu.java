package sergio.pinheiro.restaurantapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu_spi")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer menuId;

	@Column(name = "dish_name")
	private String dishName;

	@Column(name = "week")
	private Integer week;

	@Column(name = "availability")
	private boolean available;

	@Column(name = "available_meals")
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

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getWeek() {
		return week;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Integer getAvailableMeals() {
		return availableMeals;
	}

	public void setAvailableMeals(Integer availableMeals) {
		this.availableMeals = availableMeals;
	}

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", dishName=" + dishName + ", week=" + week + ", available=" + available
				+ ", availableMeals=" + availableMeals + "]";
	}

}
