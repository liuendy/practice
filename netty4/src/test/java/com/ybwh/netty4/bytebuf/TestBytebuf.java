package com.ybwh.netty4.bytebuf;

import java.util.Iterator;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Netty的最佳实践是在I/O通信线程的读写缓冲区使用DirectByteBuf，后端业务消息的编解码模块使用HeapByteBuf，
 * 这样组合可以达到性能最优。
 * 
 * @author Administrator
 * 
 */
public class TestBytebuf {

	@Test
	public void testUnpooledHeapByteBuf() {

		ByteBuf heapBuffer = Unpooled.buffer(256);

		// 通过索引访问byte时不会改变真实的读索引和写索引

		// 写数据到buffer
		for (int i = 0; i < heapBuffer.capacity(); i++) {
			heapBuffer.setByte(i, i);
		}

		// 读数据
		for (int i = 0; i < heapBuffer.capacity(); i++) {
			System.out.print(heapBuffer.getByte(i) + ", ");
		}

		System.out.println();
		System.out.println(heapBuffer);

		// 只有write和read会改变读索引和写索引
		heapBuffer.writeInt(1);
		System.out.println(heapBuffer.readInt());
		System.out.println(heapBuffer);

		// 可以直接访问数组
		System.out.println(heapBuffer.array());
	}

	@Test
	public void testUnpooledDirectBuf() {
		ByteBuf directBuf = Unpooled.directBuffer(16);
		if (!directBuf.hasArray()) {
			int len = directBuf.readableBytes();
			byte[] arr = new byte[len];
			directBuf.getBytes(0, arr);
		}
	}

	@Test
	public void testCompositeByteBuf() {
		CompositeByteBuf compBuf = Unpooled.compositeBuffer();
		ByteBuf heapBuf = Unpooled.buffer(8);
		ByteBuf directBuf = Unpooled.directBuffer(16);// 添加ByteBuf到
		compBuf.addComponents(heapBuf, directBuf);
		// 删除第一个ByteBuf
//		compBuf.removeComponent(0);
		Iterator<ByteBuf> iter = compBuf.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next().toString());
		}
		
		System.out.println(compBuf.hasArray());
		
		// 使用数组访问数据,
		if (!compBuf.hasArray()) {//不能直接用array()访问
			int len = compBuf.readableBytes();
			byte[] arr = new byte[len];
			compBuf.getBytes(0, arr);
		}
	}

}
