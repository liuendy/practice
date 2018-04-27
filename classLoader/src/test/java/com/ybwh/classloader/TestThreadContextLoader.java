package com.ybwh.classloader;

import org.junit.Test;

/**
 * 
 * 线程上下文加载器的应用
 * <pre>
 * 主要是在SPI模式下使用，SPI加载实现类时，使用线程上下文加载器来避免实现类不能被加载的情况
 * </pre>
 *
 */
public class TestThreadContextLoader {
	
	
	/**
	 * 如果没有通过 setContextClassLoader(ClassLoader cl)方法进行设置的话，线程将继承其父线程的上下文类加载器。
	 * Java 应用运行的初始线程的上下文类加载器是系统类加载器(AppClassLoader)
	 * 
	 */
	@Test
	public void testDefault() {
		ClassLoader threadContextClassLoader = Thread.currentThread().getContextClassLoader();
		System.out.println("Java默认的线程上下文加载器："+threadContextClassLoader);
		
	}
	
	

}
