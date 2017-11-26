package com.ybwh.classloader;

/**
 * 自己优先加载的加载器
 * 
 * @author fanbeibei
 *
 */
public class MySelfDoClassLoader extends  ClassLoader {
	/* 
	 * 重写这个方法可以覆写java.lang.ClassLoader中默认的加载委派规则
	 */
	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		return null;
	}

}
