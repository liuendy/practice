package com.ybwh.my.exit;

public class MyTask implements Runnable {
	private boolean isRunning = true;
	private String name;

	public MyTask(String name) {
		this.name = name;
	}
	
	public void stop0(){
		isRunning = false;
	}

	@Override
	public void run() {
		
		System.out.println(name + " start!!");
		while(isRunning){
			System.out.println(name + " running!!");
		}
		
		System.out.println(name + " stop!!");
		
		
	}

}
