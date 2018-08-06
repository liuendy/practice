package com.ybwh.springboot2;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot2.demo.dao.AreaDao;


@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class Springboot2ApplicationTests {
	@Autowired
	AreaDao dao;

	@Test
	public void contextLoads() {
		try {
			Assert.assertNotNull(dao);
			
			System.out.println(dao.selectByPrimaryKey(110112));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
