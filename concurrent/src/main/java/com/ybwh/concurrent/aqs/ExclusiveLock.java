package com.ybwh.concurrent.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 用AQS实现锁推荐使用内部类继承AQS，千万不要直接继承AQS，直接继承会向锁使用者暴露一些不应使用的方法
 * 
 * 排他锁
 * 
 * 
 * @author fanbeibei
 *
 */
public class ExclusiveLock {

	/**
	 * 实现一个非公平排他锁实现父类tryAcquire和tryRelease就足够了 也就是实现得到锁和释放锁的逻辑,
	 * 
	 * 然后调用acquire(int arg) 实现阻塞的lock方法，
	 * 调用acquireInterruptibly(int arg)实现可中断的lock方法，
	 * 调用tryAcquireNanos(int arg, long nanosTimeout)实现tryLock方法
	 * 调用release实现unLock方法 
	 * 
	 * @author fanbeibei
	 *
	 */
	private class Sysc extends AbstractQueuedSynchronizer {

		@Override
		protected boolean tryAcquire(int acquires) {
			/**
			 * 就是成功将state设为1就是获得了锁，否则就是获取锁失败
			 */
			assert acquires == 1; // Otherwise unused
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());//设置排他锁的拥有者
				return true;
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int releases) {
			/**
			 * 成功将state由1设为0则成功释放锁，否则释放锁失败
			 */
			assert releases == 1; // Otherwise unused
			if (getState() == 0)//没获得锁就释放（没调用lock()就unLock()）
				throw new IllegalMonitorStateException();
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}

		
		@Override
		protected boolean isHeldExclusively() {
			/**
			 * 判断当前线程是否获得了排他锁
			 */
			return getExclusiveOwnerThread() == Thread.currentThread();
		}
	}

	private Sysc sysc;

	public ExclusiveLock() {
		sysc = new Sysc();
	}
	
	protected boolean tryRelease(int unused) {
		return sysc.tryRelease(unused);
	}

	public void lock() {
		//支持重入
		if(!sysc.isHeldExclusively()){//如果当前线程没有获得这把排他锁，则调用acquire去获取
			sysc.acquire(1);
		}
		
	}

	public boolean tryLock() {
		//支持重入
		if(!sysc.isHeldExclusively()){//如果当前线程没有获得这把排他锁，则调用acquire去获取
			return sysc.tryAcquire(1);
		}
		
		return true;
	}

	public void unlock() {
		sysc.release(1);
	}

	public boolean isLocked() {
		return sysc.isHeldExclusively();
	}

	public static void main(String[] args) {
		ExclusiveLock lock = new ExclusiveLock();

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println("to lock");
				lock.lock();

				System.out.println("444444444444444");

				lock.unlock();

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				lock.lock();
				System.out.println("233333333333333");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				lock.lock();
				
				System.out.println("unlock");

				lock.unlock();

			}
		}).start();

	}

}
