package com.shangde.service;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;

import com.shangde.httptest.HttpClientUtil;

public class TestServiceReq {
	String key = "a{XKK7Y3GN[{0lsu";
	
	@Test
	public void test() {
		
		try {
			String token =  EncryptUtil.encrypt("{\"accountType\":1,\"account\":\"zhengjing01\"}&16bit" , key);
			System.out.println("token="+token);
			
			
//			String url="http://127.0.0.1:8080/service/accountAuth.do";
//			String url="http://172.16.140.55:9000/service-web/service/accountAuth.do";
			String url ="http://172.16.101.130:28080/service-web/service/accountAuth.do";
//			String url="http://192.168.2.21:18080/service-web/service/accountAuth.do";
			Map<String, String> param = new HashMap<>();
			param.put("token", token);
			//其他参数
			param.put("accountType", "1");
			param.put("account", "zhengjing01");
			String result= HttpClientUtil.postForStr(url, param , null);
			System.out.println("result="+result);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
//	@Test
	public void testDecode() {
		String token ="SnFVcmRxQlVnNHBydldiVm1ycWNOS1FUd1RPZzNxN2t1R2lDVXdUbjVzc2Y4eDcxL203aGpSQzBNM2JkSTBkbw";
//		String token ="db_KVtr3eOsCNCTmcsTNGA";
		try {
			System.out.println(EncryptUtil.decrypt(token, key));
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchPaddingException | NoSuchAlgorithmException
				| IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
	}

}
