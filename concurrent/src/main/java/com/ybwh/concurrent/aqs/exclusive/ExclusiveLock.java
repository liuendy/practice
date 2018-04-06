package com.ybwh.concurrent.aqs.exclusive;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 用AQS实现锁推荐使用内部类继承AQS，千万不要直接继承AQS，直接继承会向锁使用者暴露一些不应使用的方法
 * 
 * 排他锁
 * 
 * 
 * @author fanbeibei
 *
 */
public class ExclusiveLock implements Lock {

	/**
	 * 实现一个排他锁实现父类tryAcquire(得到锁)、tryRelease(释放锁)和isHeldExclusively(是否的到锁)
	 * 
	 * 然后调用acquire(int arg) 实现阻塞的lock方法， 调用acquireInterruptibly(int
	 * arg)实现可中断的lockInterruptibly方法， 调用tryAcquire实现tryLock(int arg)方法
	 * 调用tryAcquireNanos(int arg, long nanosTimeout)实现tryLock(long timeout,
	 * TimeUnit unit)方法 调用release实现unLock方法
	 * 
	 * @author fanbeibei
	 *
	 */
	private class Sync extends AbstractQueuedSynchronizer {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4525872546642180350L;

		@Override
		protected boolean tryAcquire(int acquires) {
			/**
			 * 就是成功将state设为1就是获得了锁，否则就是获取锁失败
			 */
			assert acquires == 1; // Otherwise unused
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());// 设置排他锁的拥有者
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
			if (getState() == 0)// 没获得锁就释放（没调用lock()就unLock()）
				throw new IllegalMonitorStateException();
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}

		
		//-----------实现condition还需下面两个方法--------------------------------
		@Override
		protected boolean isHeldExclusively() {
			/**
			 * 判断当前线程是否获得了排他锁,只有用到condition才需要去实现它。
			 */
			return getExclusiveOwnerThread() == Thread.currentThread();
		}

		final ConditionObject newCondition() {
			return new ConditionObject();
		}
	}

	private Sync sync;

	public ExclusiveLock() {
		sync = new Sync();
	}

	protected boolean tryRelease(int unused) {
		return sync.tryRelease(unused);
	}

	@Override
	public void lock() {
		// 支持重入
		if (!sync.isHeldExclusively()) {// 如果当前线程没有获得这把排他锁，则调用acquire去获取
			sync.acquire(1);
		}

	}

	@Override
	public boolean tryLock() {
		// 支持重入
		if (!sync.isHeldExclusively()) {// 如果当前线程没有获得这把排他锁，则调用acquire去获取
			return sync.tryAcquire(1);
		}

		return true;
	}

	@Override
	public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
		if (!sync.isHeldExclusively()) {// 如果当前线程没有获得这把排他锁，则调用acquire去获取
			return sync.tryAcquireNanos(1, unit.toNanos(timeout));
		}
		return true;
	}

	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	public boolean isLocked() {
		return sync.isHeldExclusively();
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
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
				lock.lock();
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
