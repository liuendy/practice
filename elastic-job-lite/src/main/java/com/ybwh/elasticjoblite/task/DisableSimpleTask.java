package com.ybwh.elasticjoblite.task;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 禁止启动任务
 */
public class DisableSimpleTask implements SimpleJob {
	private static final Logger LOGGER = Logger.getLogger(DisableSimpleTask.class);

	@Override
	public void execute(ShardingContext context) {
		
		LOGGER.debug("禁止启动任务启动  thread:"+Thread.currentThread().getName()+"  start ！！！");
	}

}
