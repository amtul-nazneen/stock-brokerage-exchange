package app.dao;


public class User {
	
	    private Integer USER_ID;
	    private String USERNAME;
	    private String PWD ;
	    private String  EMAIL ;
	    private String  CONTACT_NO  ;
	    private String  ADDRESS  ;
	    private Double BALANCE ;
	    public User() {
	    }
		public Integer getUSER_ID() {
			return USER_ID;
		}
		public void setUSER_ID(Integer uSER_ID) {
			USER_ID = uSER_ID;
		}
		public String getUSERNAME() {
			return USERNAME;
		}
		public void setUSERNAME(String uSERNAME) {
			USERNAME = uSERNAME;
		}
		public String getPWD() {
			return PWD;
		}
		public void setPWD(String pWD) {
			PWD = pWD;
		}
		public String getEMAIL() {
			return EMAIL;
		}
		public void setEMAIL(String eMAIL) {
			EMAIL = eMAIL;
		}
		public String getCONTACT_NO() {
			return CONTACT_NO;
		}
		public void setCONTACT_NO(String cONTACT_NO) {
			CONTACT_NO = cONTACT_NO;
		}
		public String getADDRESS() {
			return ADDRESS;
		}
		public void setADDRESS(String aDDRESS) {
			ADDRESS = aDDRESS;
		}
		public Double getBALANCE() {
			return BALANCE;
		}
		public void setBALANCE(Double bALANCE) {
			BALANCE = bALANCE;
		}
	    
	}

