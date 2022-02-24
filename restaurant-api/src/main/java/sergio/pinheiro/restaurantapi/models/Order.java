package sergio.pinheiro.restaurantapi.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders_spi")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private Integer orderId;

	@Column(name = "transaction_id")
	private String transactionId; // dúvidas
	@Column(name = "dish_name")
	private String dishName;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "quantity") // dúvidas se crio classe
	private int quantity;
	@Column(name = "customer_address")
	private String customerAddress; // dúvidas se passa para classe Customer
	@Column(name = "order_date")
	private Date orderDate; // testar

//	@Autowired // dúvida
//	private OrderStatus orderStatus;

	@OneToMany(mappedBy = "")
	private List<Menu> orders = new ArrayList<>();

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

//	public OrderStatus getOrderStatus() {
//		return orderStatus;
//	}

//	public void setOrderStatus(OrderStatus orderStatus) {
//		this.orderStatus = orderStatus;
//	}

//	public List<Menu> getOrders() {
//		return orders;
//	}
//
//	public void setOrders(List<Menu> orders) {
//		this.orders = orders;
//	}

}
