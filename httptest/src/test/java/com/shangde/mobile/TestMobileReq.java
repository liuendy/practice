package com.shangde.mobile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shangde.httptest.HttpClientUtil;

public class TestMobileReq {

	protected String encodeData(String data, String version) {

		return (String) SecurityUtil.getEncryptDataForParam(data, version);
	}

	protected String decodeData(String data, String version) {
		return SecurityUtil.getDecryptDataForParam(data, version);
	}

	
	
	/**
	 * 测试打点接口
	 */
	 @Test
	public void testQuerySummaryRawScheduleTime() {
		try {
			 String url ="http://m.ehr.sunlands.com/mobile-web/attendance/remind/querySummaryRawScheduleTime.do";

			String version = "1.0";
			String data0 = "{\"mobile\":\"13734222401\"}";
			
//			String data0 = "{\"userId\":1000118210}";
			
			String data = encodeData(data0, version);
			String token = DigestUtils.md5Hex(data);
			System.out.println("&&&&&&&&&&&&&" + decodeData(data, version));

			Map<String, String> param = new HashMap<>();

			param.put("data", data);
			param.put("version", version);
			param.put("token", token);

			// Header[] headers = new Header[3];
			String result = HttpClientUtil.uploadFile(url, param, null);
			JSONObject jsonData = JSON.parseObject(result);
			System.out.println("&&&&&&&&&&&&&" + decodeData(jsonData.getString("resultMessage"), version));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * 测试打点接口
	 */
	 @Test
	public void testQueryTodayMoreScheduleTime() {
		try {
			// String url = "http://172.16.140.55:6087/mobile/token/sync";
//			String url = "http://10.247.63.32:8081/mobile-web/burypoint/buryingPointBatch.do";
//			String url = "http://127.0.0.1:8080/mobile-web/burypoint/buryingPointBatch.do";
			 String url ="http://m.ehr.sunlands.com/mobile-web/attendance/remind/queryTodayMoreScheduleTime.do";

			String version = "1.0";
//			String data0 = "{\"employeeId\":83175}";
////			String data0 = "{\"userId\":1000118210}";
			
			String data0 = "{\"employeeId\":51168}";
//			String data0 = "{\"userId\":1000085666}";
			
			String data = encodeData(data0, version);
			String token = DigestUtils.md5Hex(data);
			System.out.println("&&&&&&&&&&&&&" + decodeData(data, version));

			Map<String, String> param = new HashMap<>();

			param.put("data", data);
			param.put("version", version);
			param.put("token", token);

			// Header[] headers = new Header[3];
			String result = HttpClientUtil.uploadFile(url, param, null);
			JSONObject jsonData = JSON.parseObject(result);
			System.out.println("&&&&&&&&&&&&&" + decodeData(jsonData.getString("resultMessage"), version));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * 测试打点接口
	 */
	 @Test
	public void testBuryingPointBatch() {
		try {
			// String url = "http://172.16.140.55:6087/mobile/token/sync";
//			String url = "http://10.247.63.32:8081/mobile-web/burypoint/buryingPointBatch.do";
			String url = "http://127.0.0.1:8080/mobile-web/burypoint/buryingPointBatch.do";
			// String url ="http://m.ehr.sunlands.com/mobile-web/message/mobile/token/sync";

			String version = "1.0";
			String data0 = "[\r\n" + 
					"    {\r\n" + 
					"        \"leaveTime\": \"2018-08-03 15:54:32\",\r\n" + 
					"        \"city\": \"武汉市\",\r\n" + 
					"        \"actionTime\": \"2018-08-03 15:54:32\",\r\n" + 
					"        \"deviceType\": \"1\",\r\n" + 
					"        \"actionId\": \"-1\",\r\n" + 
					"        \"deviceId\": \"8325A4605D554555A031E194A51E2A62\",\r\n" + 
					"        \"appVersion\": \"1.1.7\",\r\n" + 
					"        \"userId\": \"1000111986\",\r\n" + 
					"        \"joinTime\": \"2018-08-03 15:54:26\",\r\n" + 
					"        \"actionKey\": \"duration_appmarketelearning\",\r\n" + 
					"        \"pageKey\": \"appmarket\",\r\n" + 
					"        \"osVersion\": \"iOS11.3.1\",\r\n" + 
					"        \"userState\": \"1\",\r\n" + 
					"        \"account\": \"77550\",\r\n" + 
					"        \"channel\": \"CS_APP_IOS\",\r\n" + 
					"        \"duration\": \"6284\",\r\n" + 
					"        \"deviceModel\": \"iPhone5S\",\r\n" + 
					"        \"networkType\": \"WIFI\"\r\n" + 
					"    }\r\n" + 
					"    {\r\n" + 
					"        \"leaveTime\": \"2018-08-03 15:54:32\",\r\n" + 
					"        \"city\": \"武汉市\",\r\n" + 
					"        \"actionTime\": \"2018-08-03 15:54:32\",\r\n" + 
					"        \"deviceType\": \"1\",\r\n" + 
					"        \"actionId\": \"-1\",\r\n" + 
					"        \"deviceId\": \"8325A4605D554555A031E194A51E2A62\",\r\n" + 
					"        \"appVersion\": \"1.1.7\",\r\n" + 
					"        \"userId\": \"1000111986\",\r\n" + 
					"        \"province\": \"湖北省\",\r\n" + 
					"        \"versionCode\": \"1170\",\r\n" + 
					"        \"joinTime\": \"2018-08-03 15:54:26\",\r\n" + 
					"        \"actionKey\": \"duration_appmarketelearning\",\r\n" + 
					"        \"pageKey\": \"appmarket\",\r\n" + 
					"        \"osVersion\": \"iOS11.3.1\",\r\n" + 
					"        \"userState\": \"1\",\r\n" + 
					"        \"account\": \"77550\",\r\n" + 
					"        \"channel\": \"CS_APP_IOS\",\r\n" + 
					"        \"duration\": \"6284\",\r\n" + 
					"        \"deviceModel\": \"iPhone5S\",\r\n" + 
					"        \"networkType\": \"WIFI\"\r\n" + 
					"    }\r\n" + 
					"]";
			String data = encodeData(data0, version);
			String token = DigestUtils.md5Hex(data);
			System.out.println("&&&&&&&&&&&&&" + decodeData(data, version));

			Map<String, String> param = new HashMap<>();

			param.put("data", data);
			param.put("version", version);
			param.put("token", token);

			// Header[] headers = new Header[3];
			String result = HttpClientUtil.uploadFile(url, param, null);
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 测试上传regid
	 */
	// @Test
	public void testUploadToken() {
		try {
			// String url = "http://172.16.140.55:6087/mobile/token/sync";
			String url = "http://127.0.0.1:6087/mobile/token/sync";
			// String url ="http://m.ehr.sunlands.com/mobile-web/message/mobile/token/sync";

			String version = "1.0";
			String data0 = "{\r\n" + "    \"employeeId\": \"71350\",\r\n"
					+ "    \"pushEvidence\": \"UdkqifCrhge/89DHwWuIcGfyWe8y7QzMT41AXiG4488=\",\r\n"
					+ "    \"os\": \"android\",\r\n" + "    \"type\": \"1\"\r\n" + "}";
			String data = encodeData(data0, version);
			String token = DigestUtils.md5Hex(data);
			System.out.println("&&&&&&&&&&&&&" + decodeData(data, version));

			Map<String, String> param = new HashMap<>();

			param.put("data", data);
			param.put("version", version);
			param.put("token", token);

			// Header[] headers = new Header[3];
			String result = HttpClientUtil.uploadFile(url, param, null);
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// @Test
	public void testUpdateMessageRead() {
		try {
			String url = "http://172.16.140.55:6087/mobile/updateMessageRead";
			// String url = "http://127.0.0.1:6087/mobile/updateMessageRead";

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long t = df.parse("2018-05-16 11:53:20").getTime();

			String version = "1.0";
			String data0 = "{\r\n" + "    \"employeeId\": \"63963\",\r\n" + "    \"lastTime\": " + t + "}";
			String data = encodeData(data0, version);
			String token = DigestUtils.md5Hex(data);
			System.out.println("&&&&&&&&&&&&&" + decodeData(data, version));

			Map<String, String> param = new HashMap<>();

			param.put("data", data);
			param.put("version", version);
			param.put("token", token);

			// Header[] headers = new Header[3];
			String result = HttpClientUtil.uploadFile(url, param, null);
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetWorkMessageList() {
		try {
//			String url = "http://172.16.140.55:6087/mobile/getWorkMessageList";
//			 String url = "http://127.0.0.1:6087/message/mobile/getWorkMessageList";

			 String url =
			 "http://m.ehr.sunlands.com/mobile-web/message/mobile/getWorkMessageList";

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long t = df.parse("2018-09-06 19:31:44").getTime();

			String version = "1.0";
			 String data0 = "{\r\n" + " \"employeeId\": \"71350\",\r\n" + " \"limit\": \"10\","
			 + " \"lastTime\":\""+t+"\","
			 		+ "\"type\":0}";
//			String data0 = "{\"employeeId\":\"113063\",\"lastTime\":\"2018-07-09 10:00:00\",\"limit\":\"10\",\"type\":\"0\"}";
			String data = encodeData(data0, version);
			String token = DigestUtils.md5Hex(data);
			System.out.println("&&&&&&&&&&&&&" + decodeData(data, version));

			Map<String, String> param = new HashMap<>();

			param.put("data", data);
			param.put("version", version);
			param.put("token", token);

			// Header[] headers = new Header[3];
			String result = HttpClientUtil.uploadFile(url, param, null, "UTF-8", 30000, null);
			JSONObject jsonData = JSON.parseObject(result);
			
			

			System.out.println("&&&&&&&&&&&&&" + decodeData(jsonData.getString("resultMessage"), version));
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetWorkMessageCount() {
		try {
			String url = "http://172.16.140.55:6087/mobile/getWorkMessageCount";
			// String url = "http://127.0.0.1:6087/mobile/getWorkMessageCount";
			// String url = "http://172.16.100.199:6087/message/mobile/getWorkMessageCount";

			// DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// long t = df.parse("2018-05-15 15:04:01").getTime();

			String version = "1.0";
			String data0 = "{" + "    \"employeeId\": \"71350\"," + "    \"lastTime\": " + 0 + "}";
			String data = encodeData(data0, version);
			String token = DigestUtils.md5Hex(data);
			System.out.println("&&&&&&&&&&&&&" + decodeData(data, version));

			Map<String, String> param = new HashMap<>();

			param.put("data", data);
			param.put("version", version);
			param.put("token", token);

			// headers[0] = new
			// BasicHeader("content-type","application/x-www-form-urlencoded");

			String result = HttpClientUtil.uploadFile(url, param, null, "UTF-8", 30000, null);
			System.out.println(result);

			JSONObject jsonData = JSON.parseObject(result);

			System.out.println("&&&&&&&&&&&&&" + decodeData(jsonData.getString("resultMessage"), version));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void testGetWorkMessageType() {
		try {
			String url = "http://172.16.140.55:6087/mobile/workMessageType";
			// String url = "http://127.0.0.1:6087/message/mobile/workMessageType";

			// DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// long t = df.parse("2018-05-15 15:04:01").getTime();

			String version = "1.0";
			String data = encodeData("", version);
			String token = DigestUtils.md5Hex(data);
			System.out.println("&&&&&&&&&&&&&" + decodeData(data, version));

			Map<String, String> param = new HashMap<>();

			param.put("data", data);
			param.put("version", version);
			param.put("token", token);

			String result = HttpClientUtil.uploadFile(url, param, null);
			System.out.println(result);

			JSONObject jsonData = JSON.parseObject(result);

			System.out.println("&&&&&&&&&&&&&" + decodeData(jsonData.getString("resultMessage"), version));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryEmployeeDeptAndPost() {
		try {
			// String url =
			// "http://127.0.0.1:8080/mobile-web/personHome/queryEmployeeDeptAndPost.do";

			String url = "http://172.16.140.55:9000/mobile-web/personHome/queryEmployeeDeptAndPost.do";

			String version = "1.1.2";

			Map<String, String> param = new HashMap<>();

			String data = encodeData("{\"userId\":1000112152}", version);
			param.put("data", data);
			param.put("version", version);
			String token = MD5Util.calcMd5(data);
			param.put("token", token);

			String result = HttpClientUtil.uploadFile(url, param, null, "UTF-8", 300000, null);
			System.out.println(result);
			JSONObject jsonData = JSON.parseObject(result);

			System.out.println("&&&&&&&&&&&&&" + decodeData(jsonData.getString("resultMessage"), version));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void queryEmployeeFullDept() {
		try {
			// String url =
			// "http://127.0.0.1:8080/mobile-web/personHome/queryEmployeeFullDept.do";

			String url = "http://172.16.140.55:9000/mobile-web/personHome/queryEmployeeFullDept.do";

			String version = "1.1.2";

			Map<String, String> param = new HashMap<>();

			String data = encodeData("{\"userId\":1000112152}", version);
			param.put("data", data);
			param.put("version", version);
			String token = MD5Util.calcMd5(data);
			param.put("token", token);

			String result = HttpClientUtil.uploadFile(url, param, null, "UTF-8", 300000, null);
			System.out.println(result);
			JSONObject jsonData = JSON.parseObject(result);

			System.out.println("&&&&&&&&&&&&&" + decodeData(jsonData.getString("resultMessage"), version));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void queryUserId() {

		try {
			String url = "http://mobile.staff.shangdejigou.cn/mobile-war/mobile-war/mobile_um/userManage/userLogin.action";
			Map<String, String> params = new HashMap<>();
			params.put("data", "{\"loginAccount\":\"17786517576\",\"loginPsw\":\"11111\"}");

			String result = HttpClientUtil.postForStr(url, params, null, "UTF-8", 300000, null);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
