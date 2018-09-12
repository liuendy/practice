package com.shangde.mobile;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class SecurityUtil {

	private static final String ivParameter = "0102030405060708";

	public static String encrypt(String content, String password) {
		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = password.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
			return Base64.encodeBase64URLSafeString(encrypted); // 加密

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String content, String password) {
		try {

			byte[] raw = password.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decodeBase64(content);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 功能描述：根据版本号返回解密串，低于2.5.0的版本不解密，目的是兼容老版本
	 * 
	 * @param data
	 * @param version
	 * @param token
	 * @return
	 * 
	 * @author 周赟
	 * 
	 * @since 2016年12月20日
	 * 
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	@SuppressWarnings("deprecation")
	public static String getDecryptDataForParam(String data, String version) {

		if (StringUtils.isNotBlank(data) && StringUtils.isNotBlank(version)) {
			try {

				String md5Hex = DigestUtils.md5Hex(data);
				// String dataEncrypt = MD5.getMD5(data);
				String dataEncrypt = DigestUtils.md5Hex(data);
				// if (dataEncrypt.equals(token)) {

				// String apiKey = ConfigUtil.readProperties(
				// "applicationResourse", "API_KEY");
				String apiKey = "XeXWDrMBAN[[XNDL";
				return decrypt(data, apiKey);
				// }

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
		return data;
	}

	/**
	 * 
	 * 功能描述：根据版本号返回加密串，低于2.5.0的版本不加密，目的是兼容老版本
	 * 
	 * @param data
	 * @param version
	 * @return
	 * 
	 * @author 周赟
	 * 
	 * @since 2016年12月20日
	 * 
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static Object getEncryptDataForParam(Object data, String version) {
		if (data != null && StringUtils.isNotBlank(version)) {

			String jsonData = null;
			String apiKey = "XeXWDrMBAN[[XNDL";

			/*if (data instanceof List) {
				JSONArray jsonArray = JSONArray.fromObject(data);
				jsonData = jsonArray.toString();

			} else if (data instanceof String || data instanceof Integer) {
				jsonData = data.toString();
			} else {
				JSONObject jsonObj = JSONObject.fromObject(data);
				jsonData = jsonObj.toString();
			}*/

			if (data instanceof String || data instanceof Integer) {
				jsonData = data.toString();
			}else{
				jsonData = JSON.toJSONString(data);
			}

			return encrypt(jsonData, apiKey);
		}

		return data;
	}

	public static String md5(String data) {

		return DigestUtils.md5Hex(data);
	}

	public static void main(String[] args) {

		// String data =
		// "{\n  \"loginAccount\" : \"15210156211\",\n  \"loginPsw\" : \"111111\"\n}";
		// System.out.println(encrypt("liangjianan", "sunlandsgogo2017"));
		// System.out
		// .println(decrypt("OPrnAqKTU6dz4ZZwIcgj1A", "sunlandsgogo2017"));

		//
		// String data =
		// "{\"userId\":\"1000000001\",\"osVersion\":\"Android-24\",\"appVersion\":\"1.0.1\"}";
		// String decryptDataForParam = getDecryptDataForParam(
		// "qnfkdRDg-CkCUw7lfr01nE1EgZuK8We0iK9bf4CouWy8ijok8iu41rt7CnQ5GKd0JdpGjFkW5AwmDiW9LSJgI12WU_rIQ4uXYH3IxgSu1Co",
		// "11111111111", "111111111111");

		// System.out.println(getEncryptDataForParam(data, "1111111"));
	}

}