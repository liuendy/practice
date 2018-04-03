package com.ybwh.elasticjoblite.listener;


import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

/**
 * 本地监听器只在节点执行自己分片的时候调度，每个分片任务调度的时候本地监听器都会执行。
 */
public class LocalTaskListener implements ElasticJobListener {

	private static final Logger LOGGER = Logger.getLogger(LocalTaskListener.class);
	
	/**
     * 作业执行前的执行的方法.
     * 
     * @param shardingContexts 分片上下文
     */
	@Override
	public void beforeJobExecuted(ShardingContexts shardingContexts) {
		LOGGER.info(String.format("开始调度任务[%s]", shardingContexts.getJobName()));
		
	}

	/**
     * 作业执行后的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
	@Override
	public void afterJobExecuted(ShardingContexts shardingContexts) {
		LOGGER.info(String.format("任务[%s]调度完成", shardingContexts.getJobName()));
		
	}

}
