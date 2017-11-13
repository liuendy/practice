package com.ybwh.concurrent.aqs.my;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MySemaphore {

	private class Sync extends AbstractQueuedSynchronizer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 468448499566596383L;

		public Sync(int permits) {
			setState(permits);
		}

		final int getPermits() {
			return getState();
		}

		@Override
		protected int tryAcquireShared(int acquires) {
			for (;;) {
				int available = getState();
				int remaining = available - acquires;
				if (remaining < 0 || compareAndSetState(available, remaining))
					return remaining;
			}
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

	private Sync sync;

	public MySemaphore(int permits) {
		sync = new Sync(permits);
	}
}
