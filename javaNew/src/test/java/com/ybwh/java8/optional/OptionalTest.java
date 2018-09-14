package com.ybwh.java8.optional;

import org.junit.Test;

public class OptionalTest {

	@Test
	public void testGetName() {

		User u1 = new User("dddddd", 123);
		User u2 = null;

		System.out.println(OptinalExample.getUserName(u1));
		System.out.println(OptinalExample.getUserName(u2));
	}

}
