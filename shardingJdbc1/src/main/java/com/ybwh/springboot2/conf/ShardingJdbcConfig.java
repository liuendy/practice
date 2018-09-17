package com.ybwh.springboot2.conf;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

//	@Bean(value = "ds0", initMethod = "init", destroyMethod = "close")
	private DataSource ds0() throws SQLException {
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
		ds0.init();
		return ds0;

	}

	private Map<String, DataSource> createDataSourceMap() throws SQLException {
		Map<String, DataSource> dataSourceMap = new HashMap<>();

		dataSourceMap.put("ds0", ds0());

		return dataSourceMap;
	}

	@Bean
	public DataSource createDataSource() throws SQLException {

		DataSourceRule dataSourceRule = new DataSourceRule(createDataSourceMap());
		DatabaseShardingStrategy defalutDatabaseShardingStrategy = new DatabaseShardingStrategy("create_time",
				new FixedDatabaseShardingAlgorithm("ds0"));
		TableShardingStrategy tableShardingStrategy = new TableShardingStrategy("create_time",
				new DatetimeSingleKeyTableShardingAlgorithm("t_report", new SimpleDateFormat("yyyyMM")));

		TableRule reportTableRule = TableRule.builder("t_report")
//				 .actualTables(Arrays.asList("t_report201808","t_report201809"))
				.dynamic(true)  //动态分表
				.generateKeyColumn("id")
				.dataSourceRule(dataSourceRule).databaseShardingStrategy(defalutDatabaseShardingStrategy)
				.tableShardingStrategy(tableShardingStrategy).build();

		Collection<TableRule> tableRuleList = Arrays.asList(reportTableRule);
		ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule).tableRules(tableRuleList)
				.databaseShardingStrategy(defalutDatabaseShardingStrategy).tableShardingStrategy(tableShardingStrategy)
				.build();
		return ShardingDataSourceFactory.createDataSource(shardingRule);

	}

}
