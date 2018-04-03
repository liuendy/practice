package com.ybwh.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * Future最早来源于JDK的java.util.concurrent.Future，它用于代表异步操作的结果。
 * 
 * 可以通过get方法获取操作结果，如果操作尚未完成，则会同步阻塞当前调用的线程；如果不允许阻塞太长时间或者无限期阻塞，
 * 可以通过带超时时间的get方法获取结果；如果到达超时时间操作仍然没有完成，则抛出TimeoutException。
 * 通过isDone()方法可以判断当前的异步操作是否完成，如果完成，无论成功与否，都返回true，否则返回false。
 * 通过cancel可以尝试取消异步操作，它的结果是未知的，如果操作已经完成，或者发生其他未知的原因拒绝取消，取消操作将会失败。
 *
 */
public class TestFuture {
	ExecutorService threadPool = Executors.newFixedThreadPool(5);

	@Test
	public void testBasic() throws InterruptedException, ExecutionException {
		Future<String> future = threadPool.submit(new Callable<String>() {

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
