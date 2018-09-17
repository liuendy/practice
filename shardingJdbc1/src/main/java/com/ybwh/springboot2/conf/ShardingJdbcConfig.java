package com.ybwh.springboot2.conf;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.ybwh.springboot2.sharding.DatetimeSingleKeyTableShardingAlgorithm;
import com.ybwh.springboot2.sharding.FixedDatabaseShardingAlgorithm;

@Configuration
public class ShardingJdbcConfig {

	@Value("${datasource.ds0.driver-class-name}")
	String driverClassName;

	@Value("${datasource.ds0.url}")
	String url;
	@Value("${datasource.ds0.username}")
	String username;
	@Value("${datasource.ds0.password}")
	String password;

	@Value("${datasource.druid.initial-size}")
	int initialSize;
	@Value("${datasource.druid.max-active}")
	int maxActive;
	@Value("${datasource.druid.min-idle}")
	int minIdle;
	@Value("${datasource.druid.max-wait}")
	int maxWait;
	@Value("${datasource.druid.pool-prepared-statements}")
	boolean poolPreparedStatements;
	@Value("${datasource.druid.max-pool-prepared-statement-per-connection-size}")
	int maxPoolPreparedStatementPerConnectionSize;
	@Value("${datasource.druid.max-open-prepared-statements}")
	int maxOpenPreparedStatements;
	@Value("${datasource.druid.validation-query}")
	String validationQuery;
	@Value("${datasource.druid.test-on-borrow}")
	boolean testOnBorrow;
	@Value("${datasource.druid.test-on-return}")
	boolean testOnReturn;
	@Value("${datasource.druid.test-while-idle}")
	boolean testWhileIdle;
	@Value("${datasource.druid.time-between-eviction-runs-millis}")
	int timeBetweenEvictionRunsMillis;
	@Value("${datasource.druid.min-evictable-idle-time-millis}")
	int minEvictableIdleTimeMillis;
	@Value("${datasource.druid.filters}")
	String filters;

	@Bean(value = "ds0", initMethod = "init", destroyMethod = "close")
	public DataSource ds0() throws SQLException {
		DruidDataSource ds0 = new DruidDataSource();
		ds0.setDriverClassName(driverClassName);
		ds0.setUrl(url);
		ds0.setUsername(username);
		ds0.setPassword(password);

		ds0.setInitialSize(initialSize);
		ds0.setMaxActive(maxActive);
		ds0.setMinIdle(minIdle);
		ds0.setMaxWait(maxWait);
		ds0.setPoolPreparedStatements(poolPreparedStatements);
		ds0.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		ds0.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		ds0.setValidationQuery(validationQuery);
		ds0.setTestOnBorrow(testOnBorrow);
		ds0.setTestOnReturn(testOnReturn);
		ds0.setTestWhileIdle(testWhileIdle);
		ds0.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		ds0.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		ds0.setFilters(filters);

		return ds0;
	}

	@Bean("dataSource")
	public DataSource createDataSource(@Qualifier("ds0") @Autowired DataSource ds0) throws SQLException {
		Map<String, DataSource> dataSourceMap = new HashMap<>();
		dataSourceMap.put("ds0", ds0);

		DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);
		DatabaseShardingStrategy defalutDatabaseShardingStrategy = new DatabaseShardingStrategy("create_time",
				new FixedDatabaseShardingAlgorithm("ds0"));
		TableShardingStrategy tableShardingStrategy = new TableShardingStrategy("create_time",
				new DatetimeSingleKeyTableShardingAlgorithm("t_report", new SimpleDateFormat("yyyyMM")));

		TableRule reportTableRule = TableRule.builder("t_report")
				// .actualTables(Arrays.asList("t_report201808","t_report201809"))
				.dynamic(true) // 动态分表
				.generateKeyColumn("id").dataSourceRule(dataSourceRule)
				.databaseShardingStrategy(defalutDatabaseShardingStrategy).tableShardingStrategy(tableShardingStrategy)
				.build();

		Collection<TableRule> tableRuleList = Arrays.asList(reportTableRule);
		ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule).tableRules(tableRuleList)
				.databaseShardingStrategy(defalutDatabaseShardingStrategy).tableShardingStrategy(tableShardingStrategy)
				.build();
		return ShardingDataSourceFactory.createDataSource(shardingRule);

	}

	@Bean("sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") @Autowired DataSource dataSource)
			throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));
//		sqlSessionFactoryBean
//				.setTypeAliasesPackage("com.ybwh.springboot2.order.model;com.ybwh.springboot2.report.model");
		
		sqlSessionFactoryBean
		.setTypeAliasesPackage(processTypeAliasesPackage("com.ybwh.springboot2.**.model"));

		sqlSessionFactoryBean.setConfigLocation(resolver.getResources("classpath*:mybatis/mybatis-config.xml")[0]);
		return sqlSessionFactoryBean;
	}

	private String processTypeAliasesPackage(String typeAliasesPackage) {
		ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();  
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);  
        typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +  
                ClassUtils.convertClassNameToResourcePath(typeAliasesPackage) + "/**/*.class";  
  
        //将加载多个绝对匹配的所有Resource  
        //将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分  
        //然后进行遍历模式匹配  
        try {  
            List<String> result = new ArrayList<String>();  
            Resource[] resources =  resolver.getResources(typeAliasesPackage);  
            if(resources != null && resources.length > 0){  
                MetadataReader metadataReader = null;  
                for(Resource resource : resources){  
                    if(resource.isReadable()){  
                       metadataReader =  metadataReaderFactory.getMetadataReader(resource);  
                        try {  
                            result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());  
                        } catch (ClassNotFoundException e) {  
                            e.printStackTrace();  
                        }  
                    }  
                }  
            }  
            if(result.size() > 0) {  
                return StringUtils.join(result.toArray(), ",");  
            }
            
            return "";
            //logger.info("d");  
        } catch (IOException e) {  
            e.printStackTrace(); 
        }
        
        return null;

	}

}
