package com.ybwh.elasticjoblite.task;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 自定义分片参数
 * <p>
 * 手动触发一个Job，但是我们手动触发的时候常常需要输入一些参数。举个栗子：我们有个日统计报表，每天凌晨统计一次，统计上一天的数据。
 * 但我们发现几天前的某一天的数据有问题，需要重跑统计。这就需要统计程序能执行指定某一天的数据。
 * </p>
 */
public class CustomShardingParamTask implements SimpleJob {
	private static final Logger LOGGER = Logger.getLogger(CustomShardingParamTask.class);

	@Override
	public void execute(ShardingContext context) {
		if(null == context.getShardingParameter()) {
			LOGGER.debug("执行无分片参数的逻辑");
		}else {
			LOGGER.debug("执行有分片参数的逻辑，当前分片index：" + context.getShardingItem() 
			+",分片参数："+context.getShardingParameter());
		}
		
		

		
	}

}
