package app.dao;

public class UserStocks {
	String companyName;
	String quantity;
	String price;
	String dateOfPurchase;
	String stockId;

	
	public UserStocks(String stockid,String companyName, String quantity, String price, String dateOfPurchase) {
		super();
		this.companyName = companyName;
		this.quantity = quantity;
		this.price = price;
		this.dateOfPurchase = dateOfPurchase;
		this.stockId=stockid;
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

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	
}
