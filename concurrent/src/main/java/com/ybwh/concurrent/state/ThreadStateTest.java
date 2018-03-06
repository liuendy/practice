package com.ybwh.concurrent.state;

public class ThreadStateTest {
	
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("*****");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
		System.out.println(t.getState()+"   " + t.isAlive());
		
		t.start();
		System.out.println(t.getState()+"   " + t.isAlive());
	}
	
	
	
	

}
