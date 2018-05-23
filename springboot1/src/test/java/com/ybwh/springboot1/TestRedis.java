package com.ybwh.springboot1;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot1.dao.WorkMessageDao;
import com.ybwh.springboot1.model.po.WorkMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {
	public static final String MESSAGE_TOKEN_KEY_PREFIX = "message:token:";

	@Autowired
	private WorkMessageDao dao;
	@Test
	public void testDao() {
		try {
			System.out.println(dao.findById(WorkMessage.class,1));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

}
