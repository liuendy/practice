package com.ybwh.springboot1;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 消息系统移动端接口启动类
 *
 */
@SpringBootApplication
@ServletComponentScan(basePackages = { "com.shangde.ehr.message" })
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		logger.error("----------------error-----------------");
		logger.warn("----------------warn-----------------");
		logger.info("----------------info-----------------");
		logger.debug("----------------debug-----------------");
		logger.trace("----------------trace-----------------");
		System.out.println();

		String configPath = context.getEnvironment().getProperty("spring.config.location");
		String tomcatPort = context.getEnvironment().getProperty("server.port");
		System.out.println(configPath + "," + tomcatPort);

		testLog4j2();

	}

	public static void testLog4j2() {
		System.out.println("&&&&&&&&&&&&" + LogManager.getRootLogger().getLevel());
		System.out.println("&&&&&&&&&&&&" + LogManager.getLogger("com.ybwh.springboot1").getLevel());
		// 刷新log4j2配置文件
		LoggerContext logContext = (LoggerContext) LogManager.getContext(false);
		File file = new File("E:/log4j2.xml");
		logContext.setConfigLocation(file.toURI());

		// 重新初始化Log4j2的配置上下文
		logContext.reconfigure();

		while (true) {
			logger.error("----------------error-----------------");
			logger.warn("----------------warn-----------------");
			logger.info("----------------info-----------------");
			logger.debug("----------------debug-----------------");
			logger.trace("----------------trace-----------------");
			System.out.println();

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
