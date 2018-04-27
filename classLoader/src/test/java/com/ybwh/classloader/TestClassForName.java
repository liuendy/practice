package com.ybwh.classloader;

import org.junit.Test;

public class TestClassForName {
	
	/**
	 *  Class.forName()默认是用当前类的装载器来装载类，也可以指定加载器
	 */
	@Test
	public void testDefalut() {
		try {
			System.out.println("当前类的ClassLoader:"+this.getClass().getClassLoader());
			
			Class<?> clazz = Class.forName("com.ybwh.classloader.diff.HelloImpl");
			System.out.println("Class.forName加载类的ClassLoader:"+clazz.getClassLoader());
			
			String rootDir = "F:\\practice\\classLoader\\target\\classes";
			MyTraditionalClassLoader traditionalClassLoader = new MyTraditionalClassLoader(rootDir);
			Class<?> clazz2 = Class.forName("com.ybwh.classloader.diff.HelloImpl",true,traditionalClassLoader);
			System.out.println("Class.forName2加载类的ClassLoader:"+clazz2.getClassLoader());
			
			
			MySelfDoClassLoader selfDoClassLoader = new MySelfDoClassLoader(rootDir);
			Class<?> clazz3 = Class.forName("com.ybwh.classloader.diff.HelloImpl",true,selfDoClassLoader);
			System.out.println("Class.forName3加载类的ClassLoader:"+clazz3.getClassLoader());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
