package com.ybwh.elasticjoblite.task;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 任务异常
 */
public class ExceptionSimpleTask implements SimpleJob {
	private static final Logger LOGGER = Logger.getLogger(ExceptionSimpleTask.class);

	@Override
	public void execute(ShardingContext context) {
		
		throw new IllegalArgumentException("抛出异常");
		
	}

}
