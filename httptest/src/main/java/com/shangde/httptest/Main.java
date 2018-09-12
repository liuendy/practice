package com.shangde.httptest;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("username", "fanbeibei");
		param.put("password", "1234454");
		String result = HttpClientUtil.postForStr("http://192.168.0.87:7072/ehr-web/authenticate263.do", param,null);
		
		System.out.println(result);

	}

}
