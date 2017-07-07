package com.ybwh.netty4.buffer;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

/**
 * netty ByteBuf十分强大，提供了读、写、标记、
 * 
 * @author fanbeibei
 *
 */
public class BufferOp {

	/**
	 * get不会移动读写指针，只有read和write 会移动读写指针
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static void readAndWrite() throws UnsupportedEncodingException {
		// 分配
		ByteBuf buf = Unpooled.directBuffer(30);
		System.out.println("read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex() + ",capacity:"
				+ buf.capacity());

		// 写
		byte[] data = "EEEEE十大歌手的公司".getBytes("UTF-8");
		System.out.println("data length:" + data.length);// 26
		buf.writeBytes(data);
		System.out.println("read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex() + ",capacity:"
				+ buf.capacity());

		// 读(get 不会移动读指针)
		byte[] gData = new byte[buf.readableBytes()];
		buf.getBytes(0, gData);
		System.out.println(new String(gData, "UTF-8"));
		System.out.println("read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex() + ",capacity:"
				+ buf.capacity());

		// 读(read 会移动读指针)
		byte[] rData = new byte[buf.readableBytes()];
		buf.readBytes(rData);
		System.out.println(new String(rData, "UTF-8"));
		System.out.println("read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex() + ",capacity:"
				+ buf.capacity());
		//

		buf.release();

	}

	/**
	 * 释放buf
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static void release() throws UnsupportedEncodingException {

		// 分配
		ByteBuf buf = Unpooled.directBuffer(30);
		System.out.println("read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex() + ",capacity:"
				+ buf.capacity());

		// 写
		byte[] data = "EEEEE十大歌手的公司".getBytes("UTF-8");
		System.out.println("data length:" + data.length);// 26
		buf.writeBytes(data);
		System.out.println("read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex() + ",capacity:"
				+ buf.capacity());

		// 读
		byte[] rData = new byte[buf.readableBytes()];
		buf.readBytes(rData);

		// 释放掉已读的,避免无限扩容，这个重要，拆包粘包用到
		buf.discardSomeReadBytes();
		System.out.println("read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex() + ",capacity:"
				+ buf.capacity());

		System.out.println(new String(rData, "UTF-8"));

		// 直接释放
		buf.release();
		System.out.println(buf);

	}

	/**
	 * 给buf打标记,读写操作完后可以还原
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static void mark() throws UnsupportedEncodingException {
		// 分配
		ByteBuf buf = Unpooled.directBuffer(30);
		System.out.println("read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex() + ",capacity:"
				+ buf.capacity());

		// 写
		byte[] data = "EEEEE十大歌手的公司".getBytes("UTF-8");
		System.out.println("data length:" + data.length);// 26
		buf.writeBytes(data);
		System.out.println("after write  ,read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex()
				+ ",capacity:" + buf.capacity());

		// 打标记
		buf.markReaderIndex();
		System.out.println("after mark ,read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex()
				+ ",capacity:" + buf.capacity());

		// 读
		byte[] rData = new byte[buf.readableBytes()];
		buf.readBytes(rData);
		System.out.println("after read ,read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex()
				+ ",capacity:" + buf.capacity());

		// 还原读指针
		buf.resetReaderIndex();
		System.out.println("after reset read index,read index:" + buf.readerIndex() + ",write index:"
				+ buf.writerIndex() + ",capacity:" + buf.capacity());

	}

	public static void bufUtil() throws UnsupportedEncodingException {
		// 分配
		ByteBuf buf = Unpooled.directBuffer(30);
		System.out.println("read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex() + ",capacity:"
				+ buf.capacity());

		// 写
		byte[] data = "EEEEE十大歌手的公司".getBytes("UTF-8");
		System.out.println("data length:" + data.length);// 26
		buf.writeBytes(data);
		System.out.println("after write  ,read index:" + buf.readerIndex() + ",write index:" + buf.writerIndex()
				+ ",capacity:" + buf.capacity());
		System.out.println(ByteBufUtil.hexDump(buf));
		System.out.println(ByteBufUtil.hexDump(data));
	}

	public static void main(String[] args) throws Exception {
		// readAndWrite();
		// release();
//		mark();
		bufUtil();

	}
}
