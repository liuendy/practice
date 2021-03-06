package com.ybwh.springboot2.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;


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
//	public ResultSetInterceptor sqlStatsInterceptor() {// 插件配置
//		ResultSetInterceptor interceptor = new ResultSetInterceptor();
//		return interceptor;
//	}

	@Bean("sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory(/*@Qualifier("dataSource") */@Autowired DataSource dataSource)
			throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));
		// sqlSessionFactoryBean
		// .setTypeAliasesPackage("com.ybwh.springboot2.order.model;com.ybwh.springboot2.report.model");

		sqlSessionFactoryBean.setTypeAliasesPackage(processTypeAliasesPackage("com.ybwh.springboot2.**.model"));

		sqlSessionFactoryBean.setConfigLocation(resolver.getResources("classpath*:mybatis/mybatis-config.xml")[0]);
		return sqlSessionFactoryBean;
	}

	private String processTypeAliasesPackage(String typeAliasesPackage) {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
		typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ ClassUtils.convertClassNameToResourcePath(typeAliasesPackage) + "/**/*.class";

		// 将加载多个绝对匹配的所有Resource
		// 将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分
		// 然后进行遍历模式匹配
		try {
			List<String> result = new ArrayList<String>();
			Resource[] resources = resolver.getResources(typeAliasesPackage);
			if (resources != null && resources.length > 0) {
				MetadataReader metadataReader = null;
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						metadataReader = metadataReaderFactory.getMetadataReader(resource);
						try {
							result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage()
									.getName());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (result.size() > 0) {
				return StringUtils.join(result.toArray(), ",");
			}

			return "";
			// logger.info("d");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

}