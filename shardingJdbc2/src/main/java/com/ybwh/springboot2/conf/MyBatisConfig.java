package com.ybwh.springboot2.conf;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ybwh.springboot2.common.mybatis.plugin.ResultSetInterceptor;

/**
 * Created by jackl on 2017/2/13.
 */
@Configuration
// TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
public class MyBatisConfig {

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.ybwh.springboot2.**.**.dao");
		return mapperScannerConfigurer;
	}

	// @Bean
	public ResultSetInterceptor sqlStatsInterceptor() {// 插件配置
		ResultSetInterceptor interceptor = new ResultSetInterceptor();
		return interceptor;
	}

}