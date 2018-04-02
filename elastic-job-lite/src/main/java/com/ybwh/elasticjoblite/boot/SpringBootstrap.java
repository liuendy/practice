package com.ybwh.elasticjoblite.boot;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring配置方式启动器（推荐使用）
 *
 */
public class SpringBootstrap {
	public static void main(String[] args) throws IOException {
		bootBySimpleSpring();
	}

	public static void bootBySimpleSpring() throws IOException {
		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext context1 = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml","classpath*:elastic-job.xml");

		System.in.read();
	}
}
