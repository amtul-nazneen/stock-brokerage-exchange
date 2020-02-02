package app.dao;

import java.sql.Date;

public class RecurringBuy {
	
	
	private Integer USER_ID;
	 private String TRANSACTION_TYPE;
	 private Date SCHEDULED_DATE;
	 private Date NEXT_DATE;
	    private Integer COMPANY_ID ;
	    private Integer QUANTITY ;
	    private String ACTIVE_FLAG ;
	    private String  RECURSION_PLAN;
	    
	    public RecurringBuy()
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
		public Date getSCHEDULED_DATE() {
			return SCHEDULED_DATE;
		}
		public void setSCHEDULED_DATE(Date sCHEDULED_DATE) {
			SCHEDULED_DATE = sCHEDULED_DATE;
		}
		public Date getNEXT_DATE() {
			return NEXT_DATE;
		}
		public void setNEXT_DATE(Date nEXT_DATE) {
			NEXT_DATE = nEXT_DATE;
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
		public String getACTIVE_FLAG() {
			return ACTIVE_FLAG;
		}
		public void setACTIVE_FLAG(String aCTIVE_FLAG) {
			ACTIVE_FLAG = aCTIVE_FLAG;
		}
		public String getRECURSION_PLAN() {
			return RECURSION_PLAN;
		}
		public void setRECURSION_PLAN(String rECURSION_PLAN) {
			RECURSION_PLAN = rECURSION_PLAN;
		}
	
	
	
}
