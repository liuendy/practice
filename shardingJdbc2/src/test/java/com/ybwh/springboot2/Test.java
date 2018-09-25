package com.ybwh.springboot2;

import java.math.BigDecimal;

public class Test {
	public static void main(String[] args) {
		BigDecimal b = new BigDecimal("0.00");
		
		System.out.println(b.compareTo(BigDecimal.ZERO));
	}

}
