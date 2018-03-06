package com.ybwh.concurrent.pool;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestScheduledExecutorService {
	
	
	
	public static void main(String[] args) {
		
		ScheduledThreadPoolExecutor schduledPool = new ScheduledThreadPoolExecutor(1);
		
		
		schduledPool.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("*************");
				//这里有异常未处理，ScheduledThreadPoolExecutor就会停止后续的执行
				throw new RuntimeException("fddf");
			}
		}, 1, 1, TimeUnit.SECONDS);
		
		
	}
	

}
