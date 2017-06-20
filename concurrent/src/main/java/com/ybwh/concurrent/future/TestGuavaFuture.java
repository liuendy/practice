package com.ybwh.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class TestGuavaFuture {
	@Test
	public void testBlock() {
		try {
			ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
			ListenableFuture<?> task1 = service.submit(new Runnable() {
				public void run() {
					try {
						Thread.sleep(2000);
						System.out.println("future task1 done.....");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});

			task1.get();// block until task1 compelete

			System.out.println("main task done.....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCallback() {

		try {
			ExecutorService pool = Executors.newFixedThreadPool(10);
			
			ListeningExecutorService service = MoreExecutors.listeningDecorator(pool);
			
			ListenableFuture<Integer> task = service.submit(new Callable<Integer>() {
				public Integer call() throws Exception {
					Thread.sleep(2000);
					System.out.println("future task done......");
					
					throw new Exception();
//					return 1;
				}
			});
			
			Futures.addCallback(task, new FutureCallback<Integer>() {
				
				public void onSuccess(Integer o) {
					System.out.println("异步处理成功,result=" + o);
				}

				public void onFailure(Throwable throwable) {
					System.out.println("异步处理失败,e=" + throwable);
				}
				
			},pool);
			

			System.out.println("main task done.....");
			Thread.sleep(3000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
