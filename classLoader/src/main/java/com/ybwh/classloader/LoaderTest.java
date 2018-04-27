package com.ybwh.classloader;

public class LoaderTest {
	/**
	 * <pre>
	 * jvm classLoader architecture：
		Bootstrap ClassLoader/启动类加载器 
		主要负责jdk_home/lib目录下的核心 api 或 -Xbootclasspath 选项指定的jar包装入工作。
		Extension ClassLoader/扩展类加载器 
		主要负责jdk_home/lib/ext目录下的jar包或 -Djava.ext.dirs 指定目录下的jar包装入工作。
		System ClassLoader/系统类加载器 
		主要负责java -classpath/-Djava.class.path所指的目录下的类与jar包装入工作。
		User Custom ClassLoader/用户自定义类加载器(java.lang.ClassLoader的子类) 
		在程序运行期间, 通过java.lang.ClassLoader的子类动态加载class文件, 体现java动态实时类装入特性。
		类加载器特性:
		每个ClassLoader都维护了一份自己的名称空间， 同一个名称空间里不能出现两个同名的类。
	 * </pre>
	 * 
	 */

	public static void main(String[] args) {
		try {

			System.out.println("String  classLoader:" + String.class.getClassLoader());

			System.out.println("默认的线程上下文加载器：" + Thread.currentThread().getContextClassLoader());
			System.out.println("系统类加载器 :"+ClassLoader.getSystemClassLoader());
			System.out.println("系统类加载器的父加载器 :"+ClassLoader.getSystemClassLoader().getParent());
			System.out.println("扩展类加载器的父类  :"+ClassLoader.getSystemClassLoader().getParent().getParent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过以上的代码输出，我们可以判定系统类加载器的父加载器是标准扩展类加载器， 但是我们试图获取标准扩展类加载器的父类加载器时确得到了null，就是说标准
	 * 扩展类加载器本身强制设定父类加载器为null。
	 */
}