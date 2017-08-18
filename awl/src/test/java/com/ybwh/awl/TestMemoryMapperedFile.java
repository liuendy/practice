package com.ybwh.awl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.junit.Test;

/**
 * 测试内存映射文件
 * 
 * @author fanbeibei
 */
public class TestMemoryMapperedFile {
	@Test
	public void test(){
		try {
			RandomAccessFile raf = new RandomAccessFile("test.txt", "rw");
			FileChannel fc = raf.getChannel();
			//将test.txt文件所有数据映射到虚拟内存，并只读
			MappedByteBuffer mbyteBuf = fc.map(MapMode.READ_ONLY, 0, fc.size());
			
			
			
			
			
			
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
