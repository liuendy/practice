package com.ybwh.springboot1;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot1.dao.OrderDao;
import com.ybwh.springboot1.model.po.Order;



@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class TestUnShardingTable {
	
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
			orderDao.insert(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelect() {
		Assert.assertNotNull(orderDao);
		
		/**
		 * 单列分表只要条件中带有分表列就可以查询成功，否则无法查询成功
		 */
		try {
			Order o = orderDao.findById(Order.class, 6L);
			System.out.println("%%%%%%%%"+o.toString());
			
			System.out.println("----------------------------------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
