package com.shangde.httptest;

import org.junit.Test;

public class TestQueryEmployeeIdBy263 {
	@Test
	public void test() {
		try {
			String json = "[\"liupenghui\"]";
			String url = "http://m.ehr.sunlands.com/mobile-web/employee/queryEmployeeIdByEmployee263.do?employee263s="
					+ json;

			String result = HttpClientUtil.getForStr(url, null, null);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
