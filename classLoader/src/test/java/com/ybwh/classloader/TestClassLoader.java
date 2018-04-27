package com.ybwh.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.ybwh.classloader.diff.Hello;

public class TestClassLoader {

	/**
	 * 测试A加载器加载接口，B加载器加载实现类，强制类型转换报ClassCastException:
	 */
	@Test
	public void testLoadByDiffClassLoader() {
		try {
			String rootDir = "F:\\practice\\classLoader\\target\\classes";
			MySelfDoClassLoader selfDoClassLoader = new MySelfDoClassLoader(rootDir);
			Class<?> implementClass = selfDoClassLoader.loadClass("com.ybwh.classloader.diff.HelloImpl");//这里抛NoClassDefFoundError
			System.out.println("implement Class loader" + implementClass.getClassLoader());

			Object implement = implementClass.newInstance();
			Method sayMethod = implementClass.getMethod("say", null);
			sayMethod.invoke(implement, null);
			
			
			System.out.println(implementClass.getClassLoader()+"-----"+implementClass.getName());
			System.out.println(Hello.class.getClassLoader()+"-----"+Hello.class.getName());
			
			Hello hi = (Hello) implement;
			

		} catch (Throwable e) {
			e.printStackTrace();
		} 

	}

}
