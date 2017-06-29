package com.ybwh.concurrent.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class SharedLock {
	
	private class Sysc extends AbstractQueuedSynchronizer{
		
	}
	
	
	private int sharedCount;
	
	
	
	public SharedLock(int sharedCount) {
		this.sharedCount = sharedCount;
	}
	
	
	

}
