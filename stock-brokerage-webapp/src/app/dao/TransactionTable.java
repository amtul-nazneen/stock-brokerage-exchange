package app.dao;

import java.sql.Date;

public class TransactionTable {
	
	private Integer USER_ID;
	 private String TRANSACTION_TYPE;
	   
	    private Integer COMPANY_ID ;
	    private Integer QUANTITY ;
	    private String  STATUS;
	    
	    public TransactionTable()
	    {
	    	
	    }
	 public Integer getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(Integer uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getTRANSACTION_TYPE() {
		return TRANSACTION_TYPE;
	}
	public void setTRANSACTION_TYPE(String tRANSACTION_TYPE) {
		TRANSACTION_TYPE = tRANSACTION_TYPE;
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
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	   
	
	
}
