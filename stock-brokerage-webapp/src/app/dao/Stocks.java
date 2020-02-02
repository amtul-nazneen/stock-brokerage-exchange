package app.dao;

import java.util.List;
public class Stocks {
    private String username;
    private String company;
    private String code;
    private List<String> stocks;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<String> getStocks() {
		return stocks;
	}
	public void setStocks(List<String> stocks) {
		this.stocks = stocks;
	}
}