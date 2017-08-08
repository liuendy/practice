package com.ybwh.fileop.lock;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class LockTask implements Runnable {

	private String name;
	private boolean isShared;
	private FileLock lock = null;
	private RandomAccessFile file = null;
	private FileChannel fChannel = null;

	private String path;

	public LockTask(String name, String path, boolean isShared) {
		this.name = name;
		this.isShared = isShared;
		this.path = path;
	}

	@Override
	public void run() {

		try {
			file = new RandomAccessFile(path, "rw");
			fChannel = file.getChannel();
			lock = fChannel.tryLock(2, 3, isShared);
			System.out.println(lock);
			if (null != lock) {
				System.out.println(name + " lock,shared:" + lock.isShared() + ",valid:" + lock.isValid() + ",position:"
						+ lock.position() + ",size:" + lock.size());
			}

			Thread.sleep(100000);

		} catch (Exception e) {
			System.out.println(name+"  Exception"+e);
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
