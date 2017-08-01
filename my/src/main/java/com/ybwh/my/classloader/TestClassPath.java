package com.ybwh.my.classloader;

import javax.swing.plaf.metal.MetalIconFactory.FileIcon16;

import org.junit.Test;

public class TestClassPath {
	@Test
	public void testPath(){
		//以"/"开头是相对classpath的路径，否则是相对当前类的路劲
		String classFilePath = this.getClass().getResource("/com/ybwh/my/Main.class").getFile();
		System.out.println(classFilePath);
		
		String classFilePath2 = this.getClass().getResource("../Main.class").getFile();
		System.out.println(classFilePath2);
		
		
		String metaInfPath = this.getClass().getResource("/META-INF").getFile();
		System.out.println(metaInfPath);
		
		
		
		
	}
	
	
	
	
	
	

	
}
