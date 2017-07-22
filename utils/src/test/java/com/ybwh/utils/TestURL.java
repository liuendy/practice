package com.ybwh.utils;

import java.net.URL;

import org.junit.Test;

public class TestURL {
	@Test
	public void testRead(){
		
		try {
//			URL u = new URL("registry://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?application=helloService-app&localhost=true&registry=zookeeper");
//		
//			System.out.println(u.getProtocol());
			
			URL url = new URL("registry", "127.0.0.1", "/test/");
			System.out.println(url.getProtocol());
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
