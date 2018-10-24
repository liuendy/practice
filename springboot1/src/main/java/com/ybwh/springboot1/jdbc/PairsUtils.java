package com.ybwh.springboot1.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PairsUtils {
	private PairsUtils() {}
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> toMap(List<Pairs> pairsList){
		if(null == pairsList) {
			return null;
		}
		
		Map<K, V> map = new HashMap<>(pairsList.size());
		for(Pairs p:pairsList) {
			map.put((K)p.getO1(),(V)p.getO2());
		}
		
		return map;
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> toMap(Pairs p){
		if(null == p) {
			return null;
		}
		
		Map<K, V> map = new HashMap<>();
		map.put((K)p.getO1(),(V)p.getO2());
		return map;
	}

}
