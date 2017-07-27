package com.ybwh.my.exit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExit {
	public static void main(String[] args) {
		
		
		
		ExecutorService threadPool = Executors.newFixedThreadPool(4);
		MyTask task1 = new MyTask("task1");
		MyTask task2 = new MyTask("task2");
		MyTask task3 = new MyTask("task3");
		MyTask task4 = new MyTask("task4");
		
		threadPool.execute(task1);
		threadPool.execute(task2);
		threadPool.execute(task3);
		threadPool.execute(task4);
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown");
            try {
            	
            	threadPool.shutdown();
            	
            	task1.stop0();
            	task2.stop0();
            	task3.stop0();
            	task4.stop0();
                while (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.out.println("线程池没有关闭");
                }

                System.out.println("线程池已经关闭");
            	
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }));
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		System.exit(0);
		
		
	}
}
