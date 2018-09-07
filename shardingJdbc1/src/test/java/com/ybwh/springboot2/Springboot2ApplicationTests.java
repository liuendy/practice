package com.ybwh.springboot2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class Springboot2ApplicationTests {


	@Test
	public void contextLoads() {
		try {
//			Assert.assertNotNull(dao);
//			
//			System.out.println(dao.selectByPrimaryKey(110112));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
