package com.ybwh.elasticjoblite.task;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 简单的单节点运行任务
 */
public class StandaloneSimpleTask implements SimpleJob {
	private static final Logger LOGGER = Logger.getLogger(StandaloneSimpleTask.class);

	@Override
	public void execute(ShardingContext context) {
		System.out.println(context.getJobName());
		/**
		 * 默认的分片策略:基于平均分配算法的分片策略,如果分片不能整除，则不能整除的多余分片将依次追加到序号小的服务器
		 */
		//当你的作业是分片的时候，你需要在你的Job的execute方法中根据当前的分片shardingItem的不同取值实现不同的逻辑，
        //要把所有的shardingItem都覆盖到，因为在分布式环境，每台机器都不能确保它当前的分片是哪一个，并且我们需要保持程序
        //的一致性，程序编写好了对部署是不会有影响的
		LOGGER.debug("当前分片index：" + context.getShardingItem() + "，总分片数：" + context.getShardingTotalCount()+",分片参数："+context.getShardingParameter());

		LOGGER.debug("thread:" + Thread.currentThread().getName());
	}

}
