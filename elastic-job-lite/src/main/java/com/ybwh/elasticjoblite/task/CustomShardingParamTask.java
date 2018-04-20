package com.ybwh.elasticjoblite.task;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 自定义分片参数
 */
public class CustomShardingParamTask implements SimpleJob {
	private static final Logger LOGGER = Logger.getLogger(CustomShardingParamTask.class);

	@Override
	public void execute(ShardingContext context) {
		System.out.println(context.getJobName());
		
		LOGGER.debug("自定义分片参数，当前分片index：" + context.getShardingItem() +",分片参数："+context.getShardingParameter());

		
	}

}
