package model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

public class Orders {
	private String userMail; //buyer's email
	private int status;
	private Date orderDate;
	private String discount;
	private String address; //buyer's address
	public Orders(String email, String discount, String address) {
		super();
		this.userMail = email;
		this.discount = discount;
		this.address = address;
		LocalDate date = LocalDate.now();
		orderDate = Date.valueOf(date);
	}
	public String getUserMail() {
		return userMail;
	}
	public int getStatus() {
		return status;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public String getDiscount() {
		return discount;
	}
	public String getAddress() {
		return address;
	}
}
