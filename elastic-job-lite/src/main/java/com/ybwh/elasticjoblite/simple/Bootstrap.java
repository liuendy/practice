package com.ybwh.elasticjoblite.simple;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bootstrap {
	public static void main(String[] args) throws IOException {
		ApplicationContext context1 = new ClassPathXmlApplicationContext("classpath*:elastic-job.xml");
		
		System.in.read();
	}
}
