package app.dao;

public class Stock {

	String price;
	String date;
	Integer quantity;
	
	
	public Stock(String date, String price) {
		super();
		this.date = date;
		this.price = price;
		this.quantity=1;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
