package com.ybwh.springboot2;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot2.report.dao.ReportDao;
import com.ybwh.springboot2.report.model.Report;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class TesDynamicShardingTable {
	
	@Autowired
	private ReportDao dao;
	
	@Test
	public void testInsert() {
		Assert.assertNotNull(dao);
		
		Report report = new Report();
		report.setCreateTime(new Date());
		report.setSaleCount(555);
		report.setSaleAmount(new BigDecimal(33333333.44D));

		
		try {
			dao.insertSelective(report);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelect() {
		Assert.assertNotNull(dao);
		
		/**
		 * 单列分表只要条件中带有分表列就可以查询成功，否则无法查询成功
		 */
		try {
			
			DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			List<Report> list = dao.selectEqCreateTime(dFormat.parse("2018-09-09 13:24:00"));
			System.out.println(list);
			
			
			
			System.out.println("----------------------------------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
