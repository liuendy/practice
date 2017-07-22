package com.ybwh.spring.practice.lifecycle;

import org.springframework.context.Lifecycle;

public class TargetBean implements Lifecycle{

	@Override
	public void start() {
		System.out.println(this.getClass()+"  start");
		
	}

	@Override
	public void stop() {
		System.out.println(this.getClass()+"stop");
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

}
