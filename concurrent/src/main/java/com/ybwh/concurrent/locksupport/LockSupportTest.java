package com.ybwh.concurrent.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Runnable r = new Runnable() {

			@Override
			public void run() {
				System.out.println("111111111111");
				//对应的blcoker会记录在Thread的一个parkBlocker属性中,通过jstack命令可以非常方便的监控具体的阻塞对象
				Object blcoker = new Object();
				LockSupport.park(blcoker);

				System.out.println("222222222222");

			}
		};

		Thread thread = new Thread(r);
		thread.start();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		LockSupport.unpark(thread);
//		thread.interrupt();//interrupt也可以解除park但不会抛异常
		
		

	}

}
