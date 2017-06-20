package com.ybwh.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;


public class TestFuture {
	ExecutorService threadPool = Executors.newFixedThreadPool(5);
	
	@Test
	public void testBasic() throws InterruptedException, ExecutionException{
		Future<String> future = threadPool.submit(new Callable<String>(){

			@Override
			public String call() throws Exception {
				System.out.println("11111111");
				
				
				return "fdsfsfsfsf";
			}
			
		});
		
		
		 String result = future.get();
		 System.out.println(result);
		
	}

}
