package com.ybwh.springboot1.jdbc;

import javax.persistence.Column;

/**
 * 成对的值对象
 *
 */
public class Pairs {
	@Column(name="o1")
	private  Object o1;
	@Column(name="o2")
	private Object o2;
	
	public Object getO1() {
		return o1;
	}
	
	public void setO1(Object o1) {
		this.o1 = o1;
	}
	
	public Object getO2() {
		return o2;
	}
	
	public void setO2(Object o2) {
		this.o2 = o2;
	}
	

	

}
