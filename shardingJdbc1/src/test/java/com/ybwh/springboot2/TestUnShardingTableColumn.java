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
public class TestUnShardingTableColumn {
	
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
		
		/**
		 * 单列分表只要条件中带有分表列就可以查询成功，否则无法查询成功
		 */
		try {
			List<Order> list = orderDao.selectByUserId(6L);
			System.out.println("%%%%%%%%"+list.toString());
			
			
			System.out.println("----------------------------------------------------------------------------------------------");
			List<Order> list2 = orderDao.selectByUserIdAndOrderId(6L,228620604381593600L);
			System.out.println("%%%%%%%%"+list2);
			
			Order o = orderDao.selectByPrimaryKey(228620718068203520L);
			System.out.println("%%%%%%%%"+o);
			
			
			System.out.println("----------------------------------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
