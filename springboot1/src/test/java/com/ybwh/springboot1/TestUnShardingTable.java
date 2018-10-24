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
import com.ybwh.springboot1.jdbc.BaseDao;
import com.ybwh.springboot1.jdbc.BaseDao.StreamDataCallback;
import com.ybwh.springboot1.jdbc.Pairs;
import com.ybwh.springboot1.jdbc.PairsUtils;
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
		
		
		List<Pairs> list = orderDao.query("select order_id o1,user_id o2 from t_order", new Object[] {}, Pairs.class);
		System.out.println(PairsUtils.toMap(list));
		
		/**
		 * 单列分表只要条件中带有分表列就可以查询成功，否则无法查询成功
		 */
		try {
			Order o = orderDao.findById(Order.class, 228620604381593600L);
			System.out.println("%%%%%%%%"+o);
			
			System.out.println("----------------------------------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectStream() {
		Assert.assertNotNull(orderDao);
		
		/**
		 * 单列分表只要条件中带有分表列就可以查询成功，否则无法查询成功
		 */
		try {
			orderDao.queryInStream("select * from t_order", BaseDao.NULL_ARGS, Order.class, new StreamDataCallback<Order>() {

				@Override
				public void process(Order obj, int rowNum) {
					System.out.println(obj);
					
				}
				
			});
			
			System.out.println("----------------------------------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
