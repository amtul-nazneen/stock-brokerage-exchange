package app.dao;

import java.io.Serializable;

public class Company implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer companyId;
	private String companyName;

	
	public Company(String companyName) {
		super();
		this.companyName = companyName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
