package com.ybwh.springboot2;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot2.report.dao.ReportDao;


/**
 * 动态分表
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class TestDynamicShardingDB {
	@Autowired
	private ReportDao reportDao;
	
	@Test
	public void test() {
		Assert.assertNotNull(reportDao);
		System.out.println("--------------------------------");
		
		
		
		
		
		
		
		
	}

}
