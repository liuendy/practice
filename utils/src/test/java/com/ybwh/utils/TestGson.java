package com.ybwh.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestGson {
	public static void main(String[] args) {
		Gson gson =new Gson();
		
		 Type type = new TypeToken<DataPackCondition.CarCondition<Integer>>() {
	        }.getType();
		
		DataPackCondition.CarCondition<Integer> cc =new DataPackCondition.CarCondition<Integer>();
		cc.setConditionName("eeeee");
		cc.setConditionValue(133444);
		
		
		Map<String, Integer> dataMap = new HashMap<>();
		dataMap.put("eee", 3343);
		dataMap.put("ddd", 32434);
		DataPackCondition.CarCondition<Map<String, Integer>> cc2 =new DataPackCondition.CarCondition<Map<String, Integer>>();
		cc2.setConditionName("ddd");
		cc2.setConditionValue(dataMap);
		
		
		DataPackCondition condition = new DataPackCondition();
		condition.setConditionList(new ArrayList<>());
		
		condition.getConditionList().add(cc);
		condition.getConditionList().add(cc2);
		String json = gson.toJson(condition);
		System.out.println(json);
		
		DataPackCondition condition2 = gson.fromJson(json, DataPackCondition.class);
		
		System.out.println(condition2);
		System.out.println(condition2.getConditionList().get(0).getConditionValue().getClass());
		
		System.out.println(condition2.getConditionList().get(1).getConditionValue());
		
	        
	}	
}
