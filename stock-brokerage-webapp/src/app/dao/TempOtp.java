package app.dao;

import java.sql.Date;

public class TempOtp {
	
	private Integer USER_ID;
    private String TEMP_CODE;
    private String USED_FLAG ;
    private Date DATE_CREATED;
    
    public TempOtp()
    {
    	
    }
	public Integer getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(Integer uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getTEMP_CODE() {
		return TEMP_CODE;
	}
	public void setTEMP_CODE(String tEMP_CODE) {
		TEMP_CODE = tEMP_CODE;
	}
	public String getUSED_FLAG() {
		return USED_FLAG;
	}
	public void setUSED_FLAG(String uSED_FLAG) {
		USED_FLAG = uSED_FLAG;
	}
	public Date getDATE_CREATED() {
		return DATE_CREATED;
	}
	public void setDATE_CREATED(Date dATE_CREATED) {
		DATE_CREATED = dATE_CREATED;
	}
    

}
