package com.ybwh.springboot2;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot2.report.dao.ReportDao;
import com.ybwh.springboot2.report.model.Report;

/**
 * 按月分表例子
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// @TestPropertySource(properties=
// {"spring.config.location=E:/application.yml"})
public class TesShardingTableByMonth {

	@Autowired
	private ReportDao dao;

	// @Test
	public void testInsert() throws ParseException {
		Assert.assertNotNull(dao);

		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Report report = new Report();
		report.setCreateTime(dFormat.parse("2018-08-19 16:27:20"));
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

//			List<Report> list = dao.selectEqCreateTime(dFormat.parse("2018-09-17 16:02:01"));
//			System.out.println(list);
//
			List<Report> list1 = dao.selectInCreateTime(
					Arrays.asList(dFormat.parse("2018-09-17 16:02:01"), dFormat.parse("2018-08-19 16:27:20")));
			System.out.println(list1);
//
//			List<Report> list2 = dao.selectBetweenCreateTime(dFormat.parse("2018-08-17 16:02:00"),
//					dFormat.parse("2018-09-17 16:27:20"));
//			System.out.println(list2);
//
//			Integer count = dao.selectCount(dFormat.parse("2018-08-16 16:02:00"), dFormat.parse("2018-09-17 16:27:20"));
//			System.out.println(count);

//			Long sum = dao.selectSum(dFormat.parse("2018-08-17 16:02:00"), dFormat.parse("2018-09-17 16:27:20"));
//			System.out.println(sum);

			System.out.println(
					"----------------------------------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
