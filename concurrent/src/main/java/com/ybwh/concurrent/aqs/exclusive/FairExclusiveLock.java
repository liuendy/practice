package com.ybwh.concurrent.aqs.exclusive;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 公平排他锁
 * 
 * @author fanbeibei
 *
 */
public class FairExclusiveLock implements Lock{
	
	
	
	private class FairSync extends AbstractQueuedSynchronizer{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 5667167886453853905L;


		@Override
		protected boolean tryAcquire(int acquires) {
			/**
			 * 公平锁与非公平锁区别就在tryAcquire:
			 * 非公平锁先抢锁，抢锁失败后再入队，
			 * 公平锁是先判断队列有没有等待线程，没有则抢锁，抢锁失败后再入队；有则直接入队
			 */
			
			final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() &&
                    compareAndSetState(0, acquires)) {//先判断队列有没有等待线程，没有则抢锁
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {//当前线程已经获得锁，更新state即可
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
		}

		@Override
		protected boolean tryRelease(int releases) {
			int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
		}

		
		@Override
		protected boolean isHeldExclusively() {
			/**
			 * 判断当前线程是否获得了排他锁
			 */
			return getExclusiveOwnerThread() == Thread.currentThread();
		}
	}
	
	
	private FairSync sync;

	public FairExclusiveLock() {
		sync = new FairSync();
	}

	@Override
	public void lock() {
		if(!sync.isHeldExclusively()){
			sync.acquire(1);
		}
		
	}

	

	@Override
	public boolean tryLock() {
		if(!sync.isHeldExclusively()){
			return sync.tryAcquire(1);
		}
		return true;
	}

	@Override
	public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
		if(!sync.isHeldExclusively()){
			return sync.tryAcquireNanos(1, unit.toNanos(timeout));
		}
		return true;
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquire(1);
	}
	
	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
 
}
