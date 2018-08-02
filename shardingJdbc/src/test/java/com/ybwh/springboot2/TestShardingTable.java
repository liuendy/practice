package com.ybwh.springboot2;

import java.util.Date;
import java.util.List;

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
//		o.setOrderId(2L);
		o.setOrderTime(new Date());
		o.setUserId(8L);
		
		try {
			orderDao.insertSelective(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelect() {
		Assert.assertNotNull(orderDao);
		
//		List<Order> list = orderDao.selectByUserId(7L);
//		
//		System.out.println(list);
		
		Order o = orderDao.selectByPrimaryKey(228620718068203520L);
		System.out.println(o);
		
	}
	

}
