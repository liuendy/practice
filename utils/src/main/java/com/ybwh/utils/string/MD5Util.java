package com.ybwh.utils.string;

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
		System.out.println(calcMd5("{\"userId\":\"1000101120\"}"));
		System.out.println(calcMd5("{\"employeeId\":\"66053\"}"));
		
		System.out.println("8k5Xj71hG0zS+FsVYE7ST1F6WVTXkl7NX1vJdvYj/2/F9zMkOpZCuVbBOinqTKiwZ0sUDOK9jnIA\\r\\n7Wnai2Q7vwyy2v7Qwwktfdm3EpsWjZsMwT3v56BIQOINRVZ5gdhr9Ka4T/bdj/3l25EqRY5O9cN9\\r\\nfNe6Qk0ZDJ2JK2uYNACNu8ZPyywFa9getV+JV6Zp5mnklnP7PS1xnFVhswCfJg==");
	}
}
