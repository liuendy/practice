package com.ybwh.spi;

import java.net.URL;
import java.util.Enumeration;

import org.junit.Test;

public class TestMetaInf {
	@Test
	public void testReadFile(){
		final String SERVICES_DIRECTORY = "META-INF/services/";
		
		try {
			//classpath下存在多个 SERVICES_DIRECTORY
			Enumeration<URL> urls = this.getClass().getClassLoader().getResources(SERVICES_DIRECTORY);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				System.out.println(url);
				
			}
			
			
			
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
