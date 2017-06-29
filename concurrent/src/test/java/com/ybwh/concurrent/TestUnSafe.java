package com.ybwh.concurrent;

import sun.misc.Unsafe;

public class TestUnSafe {

	public static void main(String[] args) {
		Unsafe unsafe = Unsafe.getUnsafe();
		System.out.println(unsafe);
	}
}
