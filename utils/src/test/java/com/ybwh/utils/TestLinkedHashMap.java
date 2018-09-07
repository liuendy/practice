package com.ybwh.utils;

import java.util.LinkedHashMap;
import java.util.Set;

import org.junit.Test;

public class TestLinkedHashMap {
	@Test
	public void test() {
		
		LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(10,0.75F,true);
		map.put(1, 1);
		map.put(2, 2);
		map.put(4, 4);
		map.put(5, 5);
		map.put(3, 3);
		
		
		for (Integer key : map.keySet()) {
			System.out.println(key);
		}
		System.out.println("-----------------------------");
		
		for (Integer val : map.values()) {
			System.out.println(val);
		}
	}

}
