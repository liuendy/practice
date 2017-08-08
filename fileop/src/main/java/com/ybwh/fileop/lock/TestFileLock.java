package com.ybwh.fileop.lock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.junit.Test;

/**
 * 文件锁可以让一个进程（不是一个线程）单独锁定文件或文件一部分
 * 
 * @author fanbeibei
 *
 */
public class TestFileLock {

	@Test
	public void testFileLock() {
		/*
		 * 如果一个线程在某个文件上获得了一个独占锁，然后第二个线程(同一个Java进程中)利用同一个打开的通道
		 * 来请求该文件的独占锁，那么第二个线程的请求也会抛出OverlappingFileLockException异常。
		 * 
		 */
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile("D:/lock", "rw");
			final FileChannel fChannel = file.getChannel();

			new Thread(new Runnable() {
				public void run() {
					try {
						FileLock lock = fChannel.tryLock(2, 3, false);
						System.out.println(lock);
						if (null != lock) {
							System.out.println("lock shared:" + lock.isShared() + ",valid:" + lock.isValid()
									+ ",position:" + lock.position() + ",size:" + lock.size());
						}

						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
						}

						if (null != lock) {
							try {
								lock.release();
							} catch (IOException e) {
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			new Thread(new Runnable() {
				public void run() {
					try {
						FileLock lock = fChannel.tryLock(2, 3, false);
						System.out.println(lock);
						if (null != lock) {
							System.out.println("lock shared:" + lock.isShared() + ",valid:" + lock.isValid()
									+ ",position:" + lock.position() + ",size:" + lock.size());
						}

						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
						}

						if (null != lock) {
							try {
								lock.release();
							} catch (IOException e) {
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
			}
			if (null != fChannel) {
				try {
					fChannel.close();
				} catch (IOException e) {
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {

			if (null != file) {
				try {
					file.close();
				} catch (IOException e) {
				}
			}

		}
	}

	@Test
	public void testLockByConcurrent() {
		/*
		 * 如果一个线程在某个文件上获得了一个独占锁，然后第二个线程(同一个Java进程中)利用一个单独打开的通道
		 * 来请求该文件的独占锁，那么第二个线程的请求会抛出OverlappingFileLockException异常。
		 * 但如果这两个线程运行在不同的Java虚拟机上，那么第二个线程会阻塞
		 * 
		 */
		new Thread(new LockTask("l1", "D:/lock", false)).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		new Thread(new LockTask("l2", "D:/lock", true)).start();

		try {
			Thread.sleep(200000);
		} catch (InterruptedException e) {
		}

	}

	@Test
	public void testLockWholeFile() {
		FileLock lock = null;
		RandomAccessFile file = null;
		FileChannel fChannel = null;
		try {
			file = new RandomAccessFile("D:/lock", "rw");
			fChannel = file.getChannel();
			lock = fChannel.tryLock();
			System.out.println(lock);
			if (null != lock) {
				System.out.println("lock shared:" + lock.isShared() + ",valid:" + lock.isValid() + ",position:"
						+ lock.position() + ",size:" + lock.size());
			}

			Thread.sleep(10000);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (null != fChannel) {
				try {
					fChannel.close();
				} catch (IOException e) {
				}
			}

			if (null != file) {
				try {
					file.close();
				} catch (IOException e) {
				}
			}

			if (null != lock) {
				try {
					lock.release();
				} catch (IOException e) {
				}
			}
		}
	}

	@Test
	public void testLockFileRegion() {
		FileLock lock = null;
		RandomAccessFile file = null;
		FileChannel fChannel = null;
		try {
			file = new RandomAccessFile("D:/lock", "rw");
			fChannel = file.getChannel();
			lock = fChannel.tryLock(2, 3, false);
			System.out.println(lock);
			if (null != lock) {
				System.out.println("lock shared:" + lock.isShared() + ",valid:" + lock.isValid() + ",position:"
						+ lock.position() + ",size:" + lock.size());
			}

			Thread.sleep(100000);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (null != fChannel) {
				try {
					fChannel.close();
				} catch (IOException e) {
				}
			}

			if (null != file) {
				try {
					file.close();
				} catch (IOException e) {
				}
			}

			if (null != lock) {
				try {
					lock.release();
				} catch (IOException e) {
				}
			}
		}
	}

}
