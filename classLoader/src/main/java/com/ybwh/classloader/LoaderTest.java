package com.ybwh.classloader;

public class LoaderTest {

	public static void main(String[] args) {
		try {
			
			System.out.println(String.class.getClassLoader());
			
			System.out.println(Thread.currentThread().getContextClassLoader());
			System.out.println(ClassLoader.getSystemClassLoader());
			System.out.println(ClassLoader.getSystemClassLoader().getParent());
			System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过以上的代码输出，我们可以判定系统类加载器的父加载器是标准扩展类加载器，
	 * 但是我们试图获取标准扩展类加载器的父类加载器时确得到了null，就是说标准 
	 * 扩展类加载器本身强制设定父类加载器为null。
	 */
}