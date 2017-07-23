package com.ybwh.spring.schema.model;

import java.util.List;

/**
 * 公司
 * 
 * @author fanbeibei
 *
 */
public class Company {
	private String name;
	private String businessLicense;
	/**
	 * 总裁
	 */
	private Employee ceo;
	
	private List<Department> depts;
	

	public Employee getCeo() {
		return ceo;
	}

	public void setCeo(Employee ceo) {
		this.ceo = ceo;
	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public List<Department> getDepts() {
		return depts;
	}
	

	public void setDepts(List<Department> depts) {
		this.depts = depts;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", businessLicense=" + businessLicense + ", ceo=" + ceo + ", depts=" + depts
				+ "]";
	}
	


}
