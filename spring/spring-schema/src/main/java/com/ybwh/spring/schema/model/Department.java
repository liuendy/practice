package com.ybwh.spring.schema.model;

/**
 * 部门
 * 
 * @author fanbeibei
 *
 */
public class Department {
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 员工数
	 */
	private int employeeCount;
	/**
	 * 部门头头
	 */
	private Employee head;

	public Employee getHead() {
		return head;
	}

	public void setHead(Employee head) {
		this.head = head;
	}

	public int getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(int employeeCount) {
		this.employeeCount = employeeCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Department [name=" + name + ", employeeCount=" + employeeCount + ", head=" + head + "]";
	}

}
