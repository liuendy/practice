package com.ybwh.elasticjoblite.task;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 控制台手动触发任务
 */
public class ConsoleTriggerTask implements SimpleJob {
	private static final Logger LOGGER = Logger.getLogger(ConsoleTriggerTask.class);

	@Override
	public void execute(ShardingContext context) {
		
		LOGGER.debug("控制台手动触发任务启动  thread:"+Thread.currentThread().getName()+"  start ！！！");
	}

}
