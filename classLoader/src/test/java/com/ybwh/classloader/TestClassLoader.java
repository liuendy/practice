package com.ybwh.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class TestClassLoader {

	/**
	 * 测试A加载器加载接口，B加载器加载实现类，看会不会发生NoClassDefFoundError
	 */
	@Test
	public void testLoadByDiffClassLoader() {
		try {
			String rootDir = "E:\\github\\practice\\classLoader\\target\\test-classes";
			MyTraditionalClassLoader traditionalClassLoader = new MyTraditionalClassLoader(rootDir);
			MySelfDoClassLoader selfDoClassLoader = new MySelfDoClassLoader(rootDir);

			Class<?> interfaceClass = traditionalClassLoader.loadClass("com.ybwh.classloader.diff.Hello");
			System.out.println("interface Class loader" + interfaceClass.getClassLoader());
			
			
			Class<?> implementClass = selfDoClassLoader.loadClass("com.ybwh.classloader.diff.HelloImpl");
			System.out.println("implement Class loader" + implementClass.getClassLoader());

			Object implement = implementClass.newInstance();
			Method sayMethod = implementClass.getMethod("say", null);

			sayMethod.invoke(implement, null);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

}
