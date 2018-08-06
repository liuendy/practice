package com.ybwh.springboot1;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot1.dao.WorkMessageDao;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class TestDao {
	public static final String MESSAGE_TOKEN_KEY_PREFIX = "message:token:";

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	private WorkMessageDao dao;
	@Test
	public void testDao() {
		try {
			dao.update("insert into t_employee_burying_point_connection(action_time) vaules(?)", new Object[]{new Date(0)});
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

}
