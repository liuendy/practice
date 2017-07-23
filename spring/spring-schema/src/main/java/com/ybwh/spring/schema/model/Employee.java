package com.ybwh.spring.schema.model;

/**
 * 员工
 * 
 * @author fanbeibei
 *
 */
public class Employee {
	private String num;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "Employee [num=" + num + ", name=" + name + "]";
	}

}
