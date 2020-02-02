package app.dao;

public class UserAccount {
	String accno;
	String routingno;
	String balance;

	public UserAccount(String acc, String routing,String balance) {
		this.accno=acc;
		this.routingno=routing;
		this.balance=balance;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getRoutingno() {
		return routingno;
	}

	public void setRoutingno(String routingno) {
		this.routingno = routingno;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
