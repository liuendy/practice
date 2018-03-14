package com.ybwh.springtask;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class TestSimple {
	
	private void mai() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		
	}

}
