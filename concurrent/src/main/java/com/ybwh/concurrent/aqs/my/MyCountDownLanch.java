package com.ybwh.concurrent.aqs.my;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 共享锁之CountDownLanch
 * 
 * @author fanbeibei
 *
 */
public class MyCountDownLanch {

	/**
	 * 共享锁的state字段的数值代表持有锁的线程数
	 * 
	 * 共享锁只需实现tryAcquireShared(int acquires)和tryReleaseShared(int releases)即可
	 * 
	 * 
	 * @author fanbeibei
	 *
	 */
	private class SharedSync extends AbstractQueuedSynchronizer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1859381006502600018L;

		public SharedSync(int count) {
			setState(count);
		}

		public int getCount() {
			return getState();
		}

		@Override
		protected int tryAcquireShared(int acquires) {
			return (getState() == 0) ? 1 : -1;
		}

		@Override
		protected boolean tryReleaseShared(int releases) {
			// Decrement count; signal when transition to zero
			for (;;) {
				int c = getState();
				if (c == 0)
					return false;
				int nextc = c - 1;
				if (compareAndSetState(c, nextc))
					return nextc == 0;
			}
		}

	}

	private SharedSync sync;

	public MyCountDownLanch(int count) {
		sync = new SharedSync(count);
	}

	public void await() throws InterruptedException {
		sync.acquireSharedInterruptibly(1);
	}

	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
	}

	public void countDown() {
		sync.releaseShared(1);
	}
	
	public int getCount(){
		return sync.getCount();
	}
	
	public String toString() {
        return super.toString() + "[Count = " + sync.getCount() + "]";
    }

}
