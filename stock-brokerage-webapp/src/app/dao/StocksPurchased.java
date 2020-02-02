package app.dao;

import java.sql.Date;

public class StocksPurchased {

	private Integer USER_ID;
    private Date DATE_OF_PURCHASE;
  
    private Double  PRICE  ;
    private Integer  COMPANY_ID  ;
    private Integer QUANTITY ;
    
    public StocksPurchased()
    {
    	
    }

    public Integer getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(Integer uSER_ID) {
		USER_ID = uSER_ID;
	}
	public Date getDATE_OF_PURCHASE() {
		return DATE_OF_PURCHASE;
	}
	public void setDATE_OF_PURCHASE(Date dATE_OF_PURCHASE) {
		DATE_OF_PURCHASE = dATE_OF_PURCHASE;
	}
	public Double getPRICE() {
		return PRICE;
	}
	public void setPRICE(Double pRICE) {
		PRICE = pRICE;
	}
	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}
	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}
	public Integer getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(Integer qUANTITY) {
		QUANTITY = qUANTITY;
	}
	
   
	
	
	
}
