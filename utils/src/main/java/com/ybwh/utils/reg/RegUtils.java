package com.ybwh.utils.reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具
 *
 */
public class RegUtils {

	/**
	 * 版本号
	 */
	public static Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\s*\\{?\\s*([\\._0-9a-zA-Z]+)\\s*\\}?");

	/**
	 * 包名
	 */
	public static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([$_a-zA-Z][$_a-zA-Z0-9\\.]*);");

	/**
	 * 类名
	 */
	public static final Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*)\\s+");

	/**
	 * 邮编
	 */
	public static final Pattern POST_CODE_PATTERN = Pattern.compile("^[1-9]\\d{5}$");

	/**
	 * 邮箱
	 */
	public static final Pattern EMAIL_PATTERN = Pattern
			.compile("^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}$");

	/**
	 * 用户名（字母开头 + 数字/字母/下划线)
	 */
	public static final Pattern USER_NAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z1-9_-]+$");

	/**
	 * 网址
	 */
	public static final Pattern HTTP_URL_PATTERN = Pattern
			.compile("^((http|https)://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$");

	/**
	 * 18位身份证号
	 */
	public static final Pattern ID_NUM_PATTERN = Pattern
			.compile("^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$");

	public static void main(String[] args) {

		// 要验证的字符串
		String str = "service@xsoftlab.net";
		// 邮箱验证规则
		String regEx = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 字符串是否与正则表达式相匹配
		boolean rs = matcher.matches();
		 System.out.println(matcher.group()); 
		System.out.println(rs);
		
		
		

		// 要验证的字符串
		String str1 = "baike.xsoftlab.net";
		// 正则表达式规则
		String regEx1 = "baike.*";
		// 编译正则表达式
		Pattern pattern1 = Pattern.compile(regEx1);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = pattern1.matcher(str1);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		boolean rs1 = matcher1.find();
		System.out.println(rs1);
		

		Pattern YYYYMM_PATTERN = Pattern.compile("[1-9][0-9]{3}(0[1-9]|1[1-2])\\-[1-9][0-9]{3}(0[1-9]|1[1-2])$");
		Matcher matcher3 = YYYYMM_PATTERN.matcher("200809-201809");
		//找出匹配的字符串
		while(matcher3.find()) { 
		     System.out.println(matcher3.group()); 
		} 
	}

}
