package com.ybwh.springboot1;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;
import com.ybwh.springboot1.dao.ReportDao;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class TestDataSource {
	@Autowired
    private Environment env;
	@Autowired
	private ReportDao dao;
	
	public DruidDataSource createDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
        dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
        dataSource.setInitialSize(2);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(0);
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setPoolPreparedStatements(false);
        return dataSource;
    }
	
	
	@Test
	@Transactional
	public void testConcurrentGet() {
		try {
			DruidDataSource dataSource = createDataSource();
			
			System.out.println(dataSource.getConnection());
			System.out.println(dataSource.getConnection());
			System.out.println(dao);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						System.out.println("*******"+dataSource.getConnection());
						System.out.println(dao);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			}).start();
			
			
			
			Thread.sleep(30000);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		TransactionAwareDataSourceProxy
//		TransactionInterceptor
//		TransactionSynchronizationManager
//		TransactionSynchronizationManager
		
	}
	

}
