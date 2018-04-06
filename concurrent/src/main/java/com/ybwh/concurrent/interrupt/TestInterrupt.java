package com.ybwh.concurrent.interrupt;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	 * 如果线程被LockSupport.park()暂停，线程回继续运行不会抛出异常.
	 * </p>
	 * 
	 * <p>
	 * AQS中提供了支持中断的方法
	 * <ul>
	 * <li>private void doAcquireInterruptibly(int arg) throws
	 * InterruptedException;</li>
	 * <li>private void doAcquireSharedInterruptibly(int arg) throws
	 * InterruptedException;</li>
	 * <li>private boolean doAcquireSharedNanos(int arg, long nanosTimeout)
	 * throws InterruptedException;</li>
	 * </ul>
	 * 这几个方法都抛出了InterruptedException，这些方法都会先出处中断异常，处理的代码如下：
	 * 
	 * <pre>
	 * if (Thread.interrupted())
	 * 	throw new InterruptedException();
	 * </pre>
	 * 
	 * 我们还看到有些方法并没有申请抛出InterruptedException,当它被中断时，设置了线程的中断位。
	 * 
	 * <pre>
	 * private static void selfInterrupt() {
	 * 	Thread.currentThread().interrupt();
	 * }
	 * </pre>
	 * 因此，AQS锁抛出InterruptedException的锁定是能被interrupt()触发InterruptedException的，其他锁定只能被interrupt()打个标记。
	 * 所以说对于AQS锁需要线程自己判断是否锁定和捕获InterruptedException处理中断逻辑。
	 * 
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
	 * 运行中线程是不能被中断的,正确中断方式是线程自己在内部isInterrupted()判断有没有中断标记从而实现中断逻辑。
	 */
	@Test
	public void testInterruptOnActive() {

		Thread activeThread = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 0;
				for (; i < 1000000000 && !Thread.currentThread().isInterrupted(); i++) {
				}

				System.out.println("****activeThread:" + i);
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("****activeThread exit by interrupt");
				} else {
					System.out.println("****activeThread exit normally");
				}
			}
		});
		activeThread.start();
		try {
			Thread.sleep(100);// 避免刚启动就被中断
		} catch (InterruptedException e) {
		}
		activeThread.interrupt();
		System.out.println(activeThread.isInterrupted());

		Thread activeThread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				for (; i < 1000000000; i++) {
					// System.out.println("####activeThread2:" + i + "," +
					// Thread.currentThread().isAlive() + ","
					// + Thread.currentThread().isDaemon());
				}

				System.out.println("****activeThread2:" + i);

			}
		});
		activeThread2.start();
		try {
			Thread.sleep(100);// 避免刚启动就被中断
		} catch (InterruptedException e) {
		}
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
					// 处理了InterruptedIOException，并中断当前线程
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

	/**
	 * AQS的中断需要自己处理
	 */
	@Test
	public void testAQSInterrupt() {
		Lock lock = new ReentrantLock();

		Thread aqsThread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("*********aqsThread1 ");

					lock.lock();
					if(Thread.currentThread().isInterrupted()){
						System.out.println("*********aqsThread1  interrupt");
					}
					
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}

			}

		});
		
		aqsThread1.start();
//		aqsThread1.interrupt();

		System.out.println("^^^^" + aqsThread1.isInterrupted());
		
		
		Thread aqsThread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("*********aqsThread2 ");

					Thread.sleep(1000);
					
					lock.lockInterruptibly();
					

				} catch (InterruptedException e) {
					System.out.println("*********aqsThread2  InterruptedException");
					Thread.currentThread().interrupt();
				} finally {
//					lock.unlock();
				}

			}

		});
		
		aqsThread2.start();
		aqsThread2.interrupt();

	}

}
