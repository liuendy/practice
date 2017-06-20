package com.ybwh.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class TestScheduledExecutorService 
{
    public static void main( String[] args )
    {
    	ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    	
    	scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println( "Hello World!" );				
			}
		}, 1, 4, TimeUnit.SECONDS);
        
    }
}
