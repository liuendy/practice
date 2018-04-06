package com.ybwh.concurrent.future;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
	ExecutorService threadPool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<Runnable>(5));

	/**
	 * Future模式 阻塞功能
	 */
	@Test
	public void testBlock() {
		try {
			Future<String> future = threadPool.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					System.out.println("11111111");

					return "fdsfsfsfsf";
				}

			});

			System.out.println(String.format("before block,isDone=%b, isCancelled=%b,time =%d ", future.isDone(),
					future.isCancelled(), System.currentTimeMillis()));
			String result = future.get(10, TimeUnit.SECONDS);
			System.out.println(String.format("before block,isDone=%b, isCancelled=%b,time =%d ", future.isDone(),
					future.isCancelled(), System.currentTimeMillis()));

			System.out.println("#########result=" + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Future模式 中断功能
	 * <p>
	 * 从FutureTask源码看future.cancel(mayInterruptIfRunning)
	 * 线程状态是NEW(还没运行时候)直接打个标记，返回true，同时mayInterruptIfRunning为true时还调用
	 * Thread.interrupt()来中断线程。 最后做清理工作。
	 * </p>
	 * 
	 * <p>
	 * 总结： 
	 * 1.任务没运行则可以取消返回true;
	 * 2.任务已运行，mayInterruptIfRunning为false时不能取消而且返回flase;
	 * 3.任务已运行，mayInterruptIfRunning为true时，返回true,产生效果和Thread.interrupt()一样
	 * </p>
	 * 
	 */
	@Test
	public void testCancle() {
		ExecutorService threadPool2 = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(5));
		Future<String> future1 = threadPool2.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {

				System.out.println("11111111");
				Thread.sleep(5000);
				System.out.println("11111111-----");

				return "fdsfsfsfsf";
			}

		});

		Future<String> future2 = threadPool2.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {

				System.out.println("22222222");

				int i = 0;
				for (; i < 100000000; i++) {

				}
				System.out.println(i);

				return "fdsfsfsfsf";
			}

		});

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		/**
		 * 
		 */
		System.out.println(future1.cancel(false));

		/**
		 * 任务没执行被取消则不会执行
		 */
		System.out.println(future2.cancel(true));
	}
	
	
	@Test
	public void testAQSInterrupt(){
		
	}
	
	
	

}
