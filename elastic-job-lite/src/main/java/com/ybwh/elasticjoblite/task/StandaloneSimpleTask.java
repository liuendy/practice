package com.ybwh.elasticjoblite.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 简单的单节点运行任务
 */
public class StandaloneSimpleTask implements SimpleJob {

	@Override
	public void execute(ShardingContext context) {
		System.out.println(context.getJobName());
		/**
		 * 默认的分片策略:基于平均分配算法的分片策略,如果分片不能整除，则不能整除的多余分片将依次追加到序号小的服务器
		 */
		System.out.println("当前分片index：" + context.getShardingItem() + "，总分片数：" + context.getShardingTotalCount());

		System.out.println("thread:" + Thread.currentThread().getName());
	}

}
