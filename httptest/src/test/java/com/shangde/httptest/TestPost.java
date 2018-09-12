package com.shangde.httptest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestPost {
	
	@Test
	public void testPost() {
		
		String url="http://127.0.0.1:8080/web/test.do?name=kkkk";
		Map<String, String> param = new HashMap<>();
		

//		
		
		param.put("callback", "ffff()");
//		param.put("name", "name()");
		String result= HttpClientUtil.postForStr(url, param , null);
		System.out.println(result);
		
	}

}
