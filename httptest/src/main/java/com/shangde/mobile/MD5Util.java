package com.shangde.mobile;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * 生成MD5的工具类
 * 
 * @author Fanbeibei
 *
 */
public class MD5Util {
	private static MessageDigest s_md5;

	/**
	 * 获取MD5编码
	 * @param value
	 * @return
	 */
	public static String calcMd5(String value) {

		try {
			if (s_md5 == null) {
				s_md5 = MessageDigest.getInstance("MD5");
			}

			byte[] md5 = s_md5.digest(value.getBytes("utf-8"));
			return Hex.encodeHexString(md5);
		} catch (NoSuchAlgorithmException ex) {
			// ignore
		} catch (UnsupportedEncodingException ex) {
			// ignore
		}

		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(calcMd5("{\"userId\":\"1000089999\"}"));
		System.out.println(calcMd5("{\"employeeId\":\"66053\"}"));
		System.out.println(calcMd5("[{\"account\":\"Fan\",\"pageKey\":\"test_page_ 1\",\"actionKey\":\"test_action_1\",\"actionId\":\"1\",\"actionTime\":\"2018-04-09 15:42:02\",\"appVersion\":\"5.0\",\"channel\":\"百度分享\",\"osVersion\":\"android 8.0\",\"deviceType\":1,\"deviceId\":\"wetw24223424\",\"deviceModel\":\"红米 note 5A\",\"networkType\":\"4G\",\"userId\":\"12142\",\"userState\":1,\"province\":\"湖北省\",\"city\":\"武汉市\"}]"));
		
	}
}
