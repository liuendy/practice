package com.ybwh.springboot2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class TestShardingDB {
	
	@Test
	public void test() {
		
		System.out.println("--------------------------------");
		
	}

}
