package com.ybwh.concurrent.lock;

import java.util.concurrent.Semaphore;

public class TestSemaphore {
	public static void main(String[] args) {
		Semaphore s = new Semaphore(3);
		
		for (int i=0;i < 7;i++) {
			new Thread(new WorkTask(s, "workTask"+i)).start();
		}
		
	}
	
	
	
	private static class WorkTask implements Runnable{

		private Semaphore s;
		private String threadName;
		

		public WorkTask(Semaphore s,String name) {
			this.s = s;
			this.threadName = name;
		}

		@Override
		public void run() {
			try {
				s.acquire();
				System.out.println(this.threadName+"start!!!");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(this.threadName+"end!!!");
				s.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
