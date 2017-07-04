package com.ybwh.hbase;

import java.math.BigDecimal;

import com.ybwh.hbase.spring.HbaseColumn;
import com.ybwh.hbase.spring.HbaseTable;

@HbaseTable(name = "merchant")
public class Merchant {
	@HbaseColumn(family = "base", qualifier = "name")
	String name;
	@HbaseColumn(family = "base", qualifier = "mobile")
	String mobile;
	@HbaseColumn(family = "base", qualifier = "money")
	BigDecimal money;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Merchant [name=" + name + ", mobile=" + mobile + ", money=" + money + "]";
	}

}
