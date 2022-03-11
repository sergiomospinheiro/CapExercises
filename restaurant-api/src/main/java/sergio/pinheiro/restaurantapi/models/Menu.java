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

	public Integer getMenuId() {
		return menuId;
	}

	public void setId(Integer menuId) {
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

}
