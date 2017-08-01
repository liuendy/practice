package com.yaoex.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yaoex.test.service.HelloService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:dubbo-consumer.xml" })
public class TestDubbo {

	@Autowired
	private HelloService HelloService;
	
	@Test
	public  void test(){
		HelloService.hello();
		
	}



}
