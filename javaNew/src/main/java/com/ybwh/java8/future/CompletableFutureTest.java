package com.ybwh.java8.future;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class CompletableFutureTest {

	@Test
	public void testPromiseLike() {
		CompletableFuture<String> promise = new CompletableFuture<>();
		new Thread(() -> {
			// 模拟执行耗时任务
			System.out.println("task doing...");
			try {
				Thread.sleep(3000);
				int i = 1 / 0;
			} catch (Exception e) {
				// 告诉completableFuture任务发生异常了
				promise.completeExceptionally(e);
			}
			// 告诉completableFuture任务已经完成
			promise.complete("ok");
		}).start();
		// 获取任务结果，如果没有完成会一直阻塞等待
		String result;
		try {
			result = promise.get();
			System.out.println("计算结果:" + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.out.println("执行抛出异常");
			e.printStackTrace();
		}

	}

	@Test
	public void testAll() {
		Long start = System.currentTimeMillis();
		// 结果集
		List<String> list = new ArrayList<>();

		ExecutorService threadPool = Executors.newFixedThreadPool(10);

		List<Integer> taskList = Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
		// 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
		CompletableFuture[] cfs = taskList.stream()
				.map(
						i -> 
							CompletableFuture.supplyAsync(() -> calc(i), threadPool)
								.thenApply(h -> Integer.toString(h))
								.whenComplete(
													(s, e) -> {
														System.out.println("任务" + s + "完成!result=" + s + "，异常 e=" + e + "," + new Date());
														list.add(s);
											})
					)
				.toArray(CompletableFuture[]::new);
		// 封装后无返回值，必须自己whenComplete()获取
		CompletableFuture.allOf(cfs).join();
		System.out.println("list=" + list + ",耗时=" + (System.currentTimeMillis() - start));
		for (CompletableFuture cf : cfs) {
			System.out.println(cf.join());
		}
	}

	public static Integer calc(Integer i) {
		try {
			if (i == 1) {
				Thread.sleep(3000);// 任务1耗时3秒
			} else if (i == 5) {
				Thread.sleep(5000);// 任务5耗时5秒
			} else {
				Thread.sleep(1000);// 其它任务耗时1秒
			}
			System.out.println("task线程：" + Thread.currentThread().getName() + "任务i=" + i + ",完成！+" + new Date());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return i;
	}

}
