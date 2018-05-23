package com.fbb.codegen.util;

import org.apache.commons.lang.StringUtils;

public class CodeGenUtil {
	/**
	 * 
	 * 表名转换为类名
	 * @param tableName
	 * @return
	 */
	public static String tableName2ClassName(final String tableName){
		if (StringUtils.isBlank(tableName)) {
			throw new IllegalArgumentException("tableName  can not be null!!");
		}
		
		
		String tableName0 = tableName;
		
		if (-1 == tableName0.indexOf("_")) {
			return tableName0.substring(0,1).toUpperCase() + tableName0.substring(1).toLowerCase();
		}
		
		
		while("_".startsWith(tableName0)) {
			tableName0 = tableName0.substring(1);
		}
		
		if("t_".startsWith(tableName0)) {
			tableName0 = tableName0.substring(1);
		}
		
		String [] splitArr = tableName.split("_");
		StringBuilder className = new StringBuilder();
		for (String s : splitArr) {
			if(null != s && !"".equals(s)) {
				className.append(s.substring(0,1).toUpperCase()).append(s.substring(1).toLowerCase());
			}
			
		}
		return className.toString();
	}
	
	
	/**
	 * 列名转化为类属性名
	 * @param columnName
	 * @return
	 */
	public static String columnName2PropName(final String columnName){
		if (StringUtils.isBlank(columnName)) {
			throw new IllegalArgumentException("columnName  can not be null!!");
		}
		
		if (-1 == columnName.indexOf("_")) {
			return columnName.toLowerCase();
		}
		
		String [] splitArr = columnName.split("_");
		StringBuilder propName = new StringBuilder(splitArr[0]);
		for (int i = 1; i < splitArr.length; i++) {
			propName.append(splitArr[i].substring(0,1).toUpperCase()).append(splitArr[i].substring(1).toLowerCase());
		}
		
		return propName.toString();
	}

}
