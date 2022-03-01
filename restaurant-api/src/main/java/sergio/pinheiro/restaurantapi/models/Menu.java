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
	private Integer id;

	@Column(name = "dish_name")
	private String dishName;
//	@Column(name = "available")
//	private boolean isAvailable;
	@Column(name = "week")
	private Integer week; // muitas dúvidas week.set(Calendar.WEEK_OF_YEAR, 26)

	// private Order order; // dúvidas

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public Order getOrder() {
//		return order;
//	}
//
//	public void setOrder(Order order) {
//		this.order = order;
//	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

//	public boolean isAvailable() {
//		return isAvailable;
//	}
//
//	public void setAvailable(boolean isAvailable) {
//		this.isAvailable = isAvailable;
//	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getWeek() {
		return week;
	}

}
