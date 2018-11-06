package com.ybwh.springboot2;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot2.demo.dao.BookDao;
import com.ybwh.springboot2.demo.entity.Book;


@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class BookDaoTests {
	@Autowired
	BookDao dao;

//	@Test
	public void contextLoads() {
		try {
			Assert.assertNotNull(dao);
			
			System.out.println(dao.selectById(1));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testInsert() {
		try {
			Assert.assertNotNull(dao);
			
			Book b = new Book();
			b.setBookName("算法导论");
			b.setBookCode("sfdl");
			b.setCategory(1);
			b.setIsDel(0);
			
			Date now = new Date();
			b.setCreateTime(now);
			b.setUpdateTime(now);
			b.setCreateUser(0);
			b.setUpdateUser(0);
			
			dao.insert(b);
			System.out.println(b.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testUpdate() {
		try {
			Assert.assertNotNull(dao);
			
			Book b = new Book();
			b.setId(3);
			b.setBookCode("002");
			
			Date now = new Date();
			b.setUpdateTime(now);
			
			dao.updateById(b);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
