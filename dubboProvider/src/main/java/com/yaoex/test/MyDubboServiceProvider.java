package com.yaoex.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyDubboServiceProvider {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath*:dubbo-provider.xml" });
		context.start();
		System.out.println("start.....");
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
