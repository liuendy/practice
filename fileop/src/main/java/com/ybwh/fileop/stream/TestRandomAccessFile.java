 package com.ybwh.fileop.stream;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Test;

public class TestRandomAccessFile {
	@Test
	public void test(){
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile("E:\\github\\practice\\fileop\\ee.txt","rw");
			System.out.println(file.length());
			
			System.out.println("current file point position:"+file.getFilePointer());
			String line = file.readLine();
			System.out.println(line);
			System.out.println("current file point position:"+file.getFilePointer());
			file.write("22222\n".getBytes("UTF-8"));
			System.out.println("current file point position:"+file.getFilePointer());
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				file.close();
			} catch (IOException e) {
			}
		}
		
	}

}
