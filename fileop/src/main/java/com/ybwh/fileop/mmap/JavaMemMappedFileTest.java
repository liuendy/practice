package com.ybwh.fileop.mmap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.junit.Test;

/**
 * java 内存映射文件
 * 
 * @author fanbeibei
 *
 */
public class JavaMemMappedFileTest {
	@Test
	public void testConcurrent() {

		/**
		 * 一般用多个MappedByteBuffer文件映射大文件的不同分片来多线程并行读取
		 */
		RandomAccessFile raf = null;
		FileChannel fc = null;

		try {
			raf = new RandomAccessFile("E:/github/practice/fileop/test.txt", "rw");
			System.out.println("file size :" + raf.length());
			fc = raf.getChannel();

			MappedByteBuffer mb1 = fc.map(MapMode.READ_ONLY, 0, 4 * 1024);
			MappedByteBuffer mb2 = fc.map(MapMode.READ_ONLY, 4 * 1024, raf.length() - 4 * 1024);
			System.out.println(mb2.capacity());

			new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						byte[] buf = new byte[1024];

						while (mb1.hasRemaining()) {
							mb1.get(buf, 0, buf.length < mb1.remaining() ? buf.length : mb1.remaining());
							try {
								System.out.println(new String(buf, "UTF-8"));
							} catch (UnsupportedEncodingException e) {
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();

			new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						byte[] buf = new byte[1024];
						System.out.println(
								"remain " + mb2.remaining() + ",position:" + mb2.position() + ",limit:" + mb2.limit());
						while (mb2.hasRemaining()) {

							mb2.get(buf, 0, buf.length < mb2.remaining() ? buf.length : mb2.remaining());

							try {
								System.out.println(new String(buf, "UTF-8"));
							} catch (UnsupportedEncodingException e) {
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();

			Thread.sleep(10000);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
		}

	}

	@Test
	public void testTwoWrite() {

		RandomAccessFile raf = null;
		FileChannel fc = null;
		try {
			// 打开一个文件通道，指定为只读
			raf = new RandomAccessFile("E:/github/practice/fileop/test.txt", "rw");

			System.out.println("file size :" + raf.length());
			fc = raf.getChannel();

			byte[] content = "12389".getBytes("UTF-8");
			int pos = 0;// 映射位置

			/**
			 * 两个MappedByteBuffer同时写不同的位置都会有效，写同一个位置，后写的会覆盖前写的
			 */
			MappedByteBuffer mbuff = fc.map(MapMode.READ_WRITE, pos, 1000);// 将文件撑大到1000字节
			MappedByteBuffer mbuff2 = fc.map(MapMode.READ_WRITE, pos, 1000);
			mbuff2.position(200);
			mbuff2.put("11111111111".getBytes());

			mbuff.position(200);// 从200位置开始写
			mbuff.put(content);

			System.out.println(mbuff.position());
			System.out.println("last file size :" + raf.length());

			mbuff = null;
			System.gc();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != raf) {
				try {
					raf.close();
				} catch (IOException e) {
				}
			}

			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
				}
			}
		}

	}

	@Test
	public void readFile() {
		RandomAccessFile raf = null;
		FileChannel fc = null;
		try {
			// 打开一个文件通道，指定为只读
			raf = new RandomAccessFile("E:/github/practice/fileop/test.txt", "r");

			System.out.println("file size:" + raf.length());
			fc = raf.getChannel();
			System.out.println("file channel size:" + fc.size());

			byte[] content = new byte[(int) raf.length()];

			// 一旦map执行后MappedByteBuffer就与FileChannel无关了，FileChannel关闭不会影响MappedByteBuffer
			MappedByteBuffer mbuff = fc.map(MapMode.READ_ONLY, 0, raf.length());

			System.out.println(mbuff.hasRemaining());

			mbuff.get(content);

			System.out.println(new String(content, "UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != raf) {
				try {
					raf.close();
				} catch (IOException e) {
				}
			}

			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
				}
			}
		}
	}

	@Test
	public void writeFile() {
		// 不要使用这种方式获取通道实例 ,这样有可能导致不能读写文件
		// FileInputStream fis = new FileInputStream("d:/test.txt");
		// FileChannel fileChannel = fis.getChannel();
		// ByteBuffer buff = ByteBuffer.allocate(8192);
		// fileChannel.write(buff);

		RandomAccessFile raf = null;
		FileChannel fc = null;
		try {
			// 打开一个文件通道，指定为只读
			raf = new RandomAccessFile("E:/github/practice/fileop/test.txt", "rw");

			System.out.println("file size :" + raf.length());
			fc = raf.getChannel();

			byte[] content = "12389".getBytes("UTF-8");
			int pos = 0;// 映射位置

			/**
			 * 一般MappedByteBuffer写满后再重新映射一个MappedByteBuffer扩大文件大小
			 */
			MappedByteBuffer mbuff = fc.map(MapMode.READ_WRITE, pos, 1000);// 将文件撑大到1000字节

			mbuff.position(200);// 从200位置开始写
			mbuff.put(content);

			System.out.println(mbuff.position());
			System.out.println("last file size :" + raf.length());

			mbuff = null;
			System.gc();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != raf) {
				try {
					raf.close();
				} catch (IOException e) {
				}
			}

			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
				}
			}
		}

	}

}
