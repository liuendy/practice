package com.ybwh.spring.practice.lifecycle;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath*:*.xml" });
		context.start();
		System.out.println("start.....");
		context.close();
	}

}
