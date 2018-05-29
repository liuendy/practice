package com.ybwh.guava;

import org.junit.Test;

import com.google.common.base.Objects;

/**
 * Object方法实现，如hashCode()和toString()
 *
 */
public class ObjectsTest {
	@Test
	public void test() {
		Objects.hashCode(new Object());
	}

}
