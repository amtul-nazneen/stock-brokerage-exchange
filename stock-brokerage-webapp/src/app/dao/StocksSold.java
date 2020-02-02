package app.dao;

import java.sql.Date;

public class StocksSold {
	
	    private Integer USER_ID;
	  
	    private Date SOLD_ON_DATE;
	    private Integer QUANTITY ;
	    private Double PRICE ;
	
	    private Integer COMPANY_ID ;
	    
	    public StocksSold()
	    {
	    	
	    }

		public Integer getUSER_ID() {
			return USER_ID;
		}

		public void setUSER_ID(Integer uSER_ID) {
			USER_ID = uSER_ID;
		}

		public Date getSOLD_ON_DATE() {
			return SOLD_ON_DATE;
		}

		public void setSOLD_ON_DATE(Date sOLD_ON_DATE) {
			SOLD_ON_DATE = sOLD_ON_DATE;
		}

		public Integer getQUANTITY() {
			return QUANTITY;
		}

		public void setQUANTITY(Integer qUANTITY) {
			QUANTITY = qUANTITY;
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
	
	
}
