package app.dao;

public class UserStocks {
	String companyName;
	String quantity;
	String price;
	String dateOfPurchase;
	String stockid;

	
	public UserStocks(String stockid,String companyName, String quantity, String price, String dateOfPurchase) {
		super();
		this.companyName = companyName;
		this.quantity = quantity;
		this.price = price;
		this.dateOfPurchase = dateOfPurchase;
		this.stockid=stockid;
	}

	public String getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(String dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStockid() {
		return stockid;
	}

	public void setStockid(String stockid) {
		this.stockid = stockid;
	}
	
}
