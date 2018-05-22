package com.ybwh.springboot2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot2.demo.dao.AreaDao;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot2ApplicationTests {
	@Autowired
	AreaDao dao;

	@Test
	public void contextLoads() {
		Assert.assertNotNull(dao);
	}

}
