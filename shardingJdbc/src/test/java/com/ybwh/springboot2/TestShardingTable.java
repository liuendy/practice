package com.ybwh.springboot2;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot2.order.dao.OrderDao;
import com.ybwh.springboot2.order.model.Order;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class TestShardingTable {
	
	@Autowired
	private OrderDao orderDao;
	
	@Test
	public void testInsert() {
		Assert.assertNotNull(orderDao);
		
		Order o = new Order();
		o.setOrderId(1L);
		o.setOrderTime(new Date());
		o.setUserId(5L);
		
		try {
			orderDao.insert(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
