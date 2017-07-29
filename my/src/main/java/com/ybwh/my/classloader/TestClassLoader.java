package com.ybwh.my.classloader;

import java.util.TimerTask;

import org.junit.Test;

public class TestClassLoader {

	@Test
	public void testClassLoaderName() {
		ClassLoader currentClassLoader = this.getClass().getClassLoader();//当前类加载器
		System.out.println("currentClassLoader = "+currentClassLoader);
		
		ClassLoader threadContextClassLoader = Thread.currentThread().getContextClassLoader();//当前线程的上下文加载器
		System.out.println("threadContextClassLoader="+threadContextClassLoader);
		
		/**
		 * 单线程下两者是同一个
		 */
		System.out.println(currentClassLoader==threadContextClassLoader);
	}
	
	
	@Test
	public void testThreadContextClassLoader(){
		/**
		 *  使用线程上下文加载类, 也要注意, 保证多根需要通信的线程间的类加载器应该是同一个,
		防止因为不同的类加载器, 导致类型转换异常(ClassCastException)。
		*/
		
		ClassLoader currentThreadContextClassLoader = Thread.currentThread().getContextClassLoader();//当前线程的上下文加载器
		System.out.println("currentThreadContextClassLoader="+currentThreadContextClassLoader);
		//Thread默认集成父线程(创建线程类的线程)的 Context ClassLoader，也可以通过Thread.setContextClassLoader()方法设置
		Runnable task = new Runnable() {
			public void run() {
				ClassLoader currentThreadContextClassLoader = Thread.currentThread().getContextClassLoader();//当前线程的上下文加载器
				System.out.println("ClassLoader="+currentThreadContextClassLoader);
			}
		};
		
		
		new Thread(task).start();
		new Thread(task).start();
		new Thread(task).start();
		
		
		//重置线程上下文加载器
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				ClassLoader currentThreadContextClassLoader = Thread.currentThread().getContextClassLoader();//当前线程的上下文加载器
				System.out.println("t2 ClassLoader="+currentThreadContextClassLoader);
			}
		});
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				ClassLoader currentThreadContextClassLoader = Thread.currentThread().getContextClassLoader();//当前线程的上下文加载器
				System.out.println("t1 ClassLoader="+currentThreadContextClassLoader);
				
				t2.start();
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						ClassLoader currentThreadContextClassLoader = Thread.currentThread().getContextClassLoader();//当前线程的上下文加载器
						System.out.println("t3 ClassLoader="+currentThreadContextClassLoader);
					}
				}).start();
			}
		});
		
		t1.setContextClassLoader(new ClassFileClassLoader(""));
		t1.start();
		
		
	}

}
