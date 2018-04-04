package com.ybwh.concurrent.future;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
	 * 
	 * 任务没运行则可以取消返回true，已结束、已取消或其他原因不能中断则返回false
	 * 
	 * 线程sleep是可以被Future.cacel()中断的 线程中的IO阻塞时，线程无法被Future.cancel()中断
	 * 线程中的synchronized锁阻塞时，线程无法被Future.cancel()方法中断（Lock是可以被中断的）
	 * 
	 * 【好奇】
	 * 
	 * （1）future.cancel(mayInterruptIfRunning)的内部实现会是什么样子的？可以中断一个线程池里正在执行着的“那一个”任务。
	 * 
	 * 可猜想，必定记录着具体线程标识，且发了一个中断信号。
	 * 
	 * （2）猜测，应该只是发一个中断信号，可以中断阻塞中的操作。而如果是while(true);
	 * 这样的占用CPU的非阻塞式操作，是中断不掉的，也即线程依旧在跑，占用着线程池资源。
	 * 
	 * 【注意】
	 * 
	 * a).
	 * 线程池资源有限，有些任务会submit()不进去，抛异常：java.util.concurrent.RejectedExecutionException
	 * 
	 * b).只要submit()成功的，无论是线程正在执行，或是在BlockingQueue中等待执行，future.cancel()操作均可中断掉线程。也即，与其真正执行并无关系，阻塞中或等待被调度执行中，都将被中断。
	 * 
	 * 查看FutureTask的cancel是用Thread.interrupt()方法实现的，猜想Thread.interrupt()能中断的情况cancle都能中断
	 * 
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
				Thread.currentThread().sleep(5000);
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
			Thread.currentThread().sleep(1000);
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

}
