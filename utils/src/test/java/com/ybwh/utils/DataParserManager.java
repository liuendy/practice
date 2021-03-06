package com.ybwh.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ybwh.utils.clazz.PackageUtils;

/**
 * DataParser关系管理类
 *
 * @author Aaric, created on 2017-06-27T17:11.
 * @since 2.0
 */
public class DataParserManager {

	/**
	 * 解析器关系
	 */
	private static Map<String, Class<?>> dataParserMap = new HashMap<String, Class<?>>();

	/**
	 * 注册解析器类
	 *
	 * @param tag
	 *            tag
	 * @param clazz
	 *            class
	 */
	public static void register(String tag, Class<?> clazz) {
		dataParserMap.put(tag, clazz);
	}

	/**
	 * 获得解析类
	 *
	 * @param tag
	 * @return
	 */
	public static Class<?> getDataParserClass(String tag) {
		return dataParserMap.get(tag);
	}

	private DataParserManager() {
	};

	public static void main(String[] args) {
		
		try {
			PackageUtils.loadClassesOfPackage("com.incarcloud.rooster.datapack", true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(getDataParserClass("incar-any4-1.0.0"));
	}

}
