package com.ybwh.utils.reg;

import java.util.regex.Pattern;

/**
 * 正则表达式工具
 *
 */
public class RegUtils {

	/**
	 * 版本号
	 */
	public static Pattern VARIABLE_PATTERN = Pattern.compile(
            "\\$\\s*\\{?\\s*([\\._0-9a-zA-Z]+)\\s*\\}?");
	
	/**
	 * 包名
	 */
	public static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([$_a-zA-Z][$_a-zA-Z0-9\\.]*);");

	/**
	 * 类名
	 */
	public static final Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*)\\s+");
}
