package com.ybwh.elasticjoblite.listener;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;

/**
 * 分布式监听器会在总的任务开始执行时执行一次，在总的任务结束执行时执行一次。
 *
 */
public class DistributeTaskListener extends AbstractDistributeOnceElasticJobListener {
	private static final Logger logger = Logger.getLogger(DistributeTaskListener.class);

	/**
	 * @param startedTimeoutMilliseconds
	 *            最后一个作业执行前的执行方法的超时时间 单位：毫秒
	 * @param completedTimeoutMilliseconds
	 *            最后一个作业执行后的执行方法的超时时间 单位：毫秒
	 */
	public DistributeTaskListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
		super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
	}

	/**
	 * 分布式环境中最后一个作业执行前的执行的方法.
	 *
	 * @param shardingContexts
	 *            分片上下文
	 */
	@Override
	public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
		logger.info("分布式监听器开始……");
	}

	/**
	 * 分布式环境中最后一个作业执行后的执行的方法.
	 *
	 * @param shardingContexts
	 *            分片上下文
	 */
	@Override
	public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
		logger.info("分布式监听器结束……");
	}

}
