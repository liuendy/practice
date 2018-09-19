package com.ybwh.java8.future;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * ExecutorCompletionService整合了Executor和BlockingQueue的功能。你可以将Callable任务提交给它去执行，然后使用类似于队列中的take方法获取线程的返回值。
 *
 */
public class CompletionServiceTest {
	
	private static final int POOL_SIZE = 10;
	private static final int TOTAL_TASK = 10;

	@Test
	public void test() throws InterruptedException, ExecutionException {
		// 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        CompletionService<String> cService = new ExecutorCompletionService<String>(pool);
 
        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
            cService.submit(new Callable<String>() {
				
				@Override
				public String call() throws Exception {
					return null;
				}
			});
        }
 
        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<String> future = cService.take();
            System.out.println("method2:" + future.get());
        }
 
        // 关闭线程池

	}

}
