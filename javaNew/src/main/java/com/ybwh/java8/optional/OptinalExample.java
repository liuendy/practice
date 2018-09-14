package com.ybwh.java8.optional;

import java.util.Optional;

/**
 * 正确使用 Optional
 *
 */
public class OptinalExample {
	/**
	 * 
	 * 如何获取属性
	 * 
	 * @param u
	 * @return
	 */
	public static String getUserName(User u) {
		return Optional.ofNullable(u).map(User::getName).orElse("Unknown");
	}

}
