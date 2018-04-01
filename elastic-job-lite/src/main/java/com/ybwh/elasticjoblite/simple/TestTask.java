package com.ybwh.elasticjoblite.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 简单任务
 */
public class TestTask implements SimpleJob{

	@Override
	public void execute(ShardingContext context) {
		System.out.println("dddddd");
		System.out.println(context.getShardingTotalCount());
		switch (context.getShardingItem()) {
			case 0:
				// do something by sharding item 0
				break;
			case 1:
				// do something by sharding item 1
				break;
			case 2:
				// do something by sharding item 2
				break;
			// case n: ...
		}
		
	}

}
