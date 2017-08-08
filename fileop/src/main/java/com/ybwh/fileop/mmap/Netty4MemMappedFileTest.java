package com.ybwh.fileop.mmap;

/**
 * netty4内存映射文件
 * @author fanbeibei
 *
 */
public class Netty4MemMappedFileTest {
	
	/**
	 * Netty的零拷贝

Netty的零拷贝主要体现在三个方面：

第一种实现：DirectByteBuf

就如上所说，ByteBuf可以分为HeapByteBuf和DirectByteBuf，当使用DirectByteBuf可以实现零拷贝

第二种实现：CompositeByteBuf

CompositeByteBuf将多个ByteBuf封装成一个ByteBuf，对外提供封装后的ByteBuf接口

第三种实现：DefaultFileRegion

DefaultFileRegion是Netty的文件传输类，它通过transferTo方法将文件直接发送到目标Channel，而不需要循环拷贝的方式，提升了传输性能
	 */
	
	
	public void testReadWrite(){
		
	}

}
