package com.ybwh.tranformData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.ybwh.tranformData.transform.Transformer;

/**
 * 消息系统移动端接口启动类
 *
 */
@Configuration
@SpringBootApplication
@ServletComponentScan(basePackages = { "com.ybwh.tranformData" })
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		try {
			Transformer tf = context.getBean(Transformer.class);

			System.out.println("&&&&&&&start to transform  regId........");
//			tf.startTransformMysqlToRedis();
			tf.delKey("message:token:");
//			tf.delKey("message:jump:");
//			tf.delKey("message_logo_url:");
		} catch (BeansException e) {
			e.printStackTrace();
		}

		System.exit(0);

	}

}
