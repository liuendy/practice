package com.ybwh.concurrent.interrupt;

import org.junit.Test;

/**
 * 演示Thread.interrupt()方法效果
 *
 */
public class TestInterrupt {
	/**
	 * <p>
	 * ① 如果线程处于被阻塞状态（例如处于sleep, wait, join
	 * 等状态），那么线程将立即退出被阻塞状态，并抛出一个InterruptedException异常。仅此而已。
	 * </p>
	 * <p>
	 * ②如果线程处于正常活动状态，那么会将该线程的中断标志设置为 true，仅此而已。被设置中断标志的线程将继续正常运行，不受影响。
	 * </p>
	 * 
	 * <p>
	 * 如果线程被LockSupport.park()暂停，线程回继续运行不会抛出异常，这也是AQS框架经常用Thread.isInterrupted()
	 * 判断线程是否被终止的原因.
	 * </p>
	 * 
	 */

	private Object lock = new Object();

	/**
	 * @param blockType
	 *            阻塞方式(1-sleep,2-wait,3-join)
	 */
	private void runToBlockStatus(int blockType) {

		synchronized (lock) {

			switch (blockType) {
			case 1:
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					/*
					 * 中断后线程上的中断标志会消失,所以需要手动打上标记让外界通过判断Thread.currentThread().
					 * isInterrupted()标示来决定是否终止线程还是继续下去
					 */ Thread.currentThread().interrupt();
					System.out.println("sleep Interrupted");
				}

				System.out.println("sleep Interrupted 后继续运行");
				break;
			case 2:
				try {
					lock.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.out.println("wait Interrupted");
				}
				System.out.println("wait Interrupted 后继续运行");
				break;
			case 3:
				try {
					Thread.currentThread().join();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.out.println("join Interrupted");
				}
				System.out.println("join Interrupted 后继续运行");
				break;
			}

		}

	}

	/**
	 * 传统模式中sleep, wait, join阻塞中断
	 */
	@Test
	public void testInterruptFromBlock() {
		Thread sleepThread = new Thread(new Runnable() {

			@Override
			public void run() {
				runToBlockStatus(1);
			}
		}, "sleepThread");

		sleepThread.start();
		sleepThread.interrupt();

		Thread waitThread = new Thread(new Runnable() {

			@Override
			public void run() {
				runToBlockStatus(2);
			}
		}, "waitThread");
		waitThread.start();
		waitThread.interrupt();

		Thread joinThread = new Thread(new Runnable() {
			@Override
			public void run() {
				runToBlockStatus(3);
			}
		}, "joinThread");
		joinThread.start();
		joinThread.interrupt();

	}

	/**
	 * 运行中线程是不能被中断的
	 */
	@Test
	public void testInterruptOnActive() {

		Thread activeThread = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 0;
				for (; i < 1000000000; i++) {
				}

				System.out.println("****activeThread:" + i);

			}
		});
		activeThread.start();
		activeThread.interrupt();
		System.out.println(activeThread.isInterrupted());

		
		/**
		 * 正确中断线程方式是在run方法内isInterrupted()判断
		 */
		Thread activeThread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				for (; i < 1000000000 && Thread.currentThread().isInterrupted(); i++) {
					System.out.println("####activeThread2:" + i + "," + Thread.currentThread().isAlive() + ","
							+ Thread.currentThread().isDaemon());
				}

				System.out.println("****activeThread2:" + i);

			}
		});
		activeThread2.start();
		activeThread2.interrupt();
		System.out.println(activeThread2.isInterrupted());

	}

	/**
	 * IO阻塞状态除外也是不能中断的，除非是抛出InterruptedIOException的
	 */
	@Test
	public void testInterruptOnInterruptedIOException() {
		Thread activeThread3 = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 0;
				for (; i < 1000000000; i++) {
					//处理了InterruptedIOException，并中断当前线程
					System.out.println("####activeThread3:" + i + "," + Thread.currentThread().isAlive() + ","
							+ Thread.currentThread().isDaemon());
				}

				System.out.println("****activeThread3:" + i);

			}
		});
		activeThread3.start();
		activeThread3.interrupt();
		System.out.println(activeThread3.isInterrupted());

	}

}
