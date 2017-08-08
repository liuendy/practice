package com.ybwh.fileop.stream;

import java.io.File;
import java.io.IOException;

import org.junit.Test;


/**
 * 传统流操作
 * @author fanbeibei
 *
 */
public class StreamOp {
	/**
	 * 
	 * 两个FileOutputStream两个线程并行写，后写的会覆盖先写的
	 * @throws IOException
	 */
	@Test
	public void concurrentWrite() throws IOException{
		File f = new File("E:\\github\\practice\\fileop\\ee.txt");
		
		new Thread(new StreamWriteFileTask("11111\n", f,1000)).start();
		new Thread(new StreamWriteFileTask("22222\n", f,1)).start();
		
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
