package com.ybwh.fileop.mmap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class FileChannelTest {
	@Test
	public void test(){
		RandomAccessFile f = null ;
		FileChannel fChannel = null;
		try {
			f = new RandomAccessFile("E:/github/practice/fileop/test.txt", "rw");
			fChannel = f.getChannel();
			System.out.println("当前文件位置："+fChannel.position());
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (null != fChannel) {
				try {
					fChannel.close();
				} catch (IOException e) {
				}
			}
			if (null !=f) {
				try {
					f.close();
				} catch (IOException e) {
				}
			} 
		}
	
	
	}

}
