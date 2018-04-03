package com.ybwh.elasticjoblite.task;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 简单的主从模式任务，保证任务不重复执行.
 * 如果需要防止主节点还没执行完成就挂掉的情况，那就用zookeeper进行监听选主。
 * 
 */
public class HaSimpleTask implements SimpleJob{
	private static final Logger LOGGER = Logger.getLogger(HaSimpleTask.class);

	@Override
	public void execute(ShardingContext shardingContext) {
		//谁分到分片0，谁就是主节点,
		if(0 == shardingContext.getShardingItem()) {
			LOGGER.info("主节点运行任务");
		}else {
			LOGGER.info("从节点节点直接结束");
		}
		
	}
	
	
	/**
	 * 用zookeeper防止主节点还没执行完成就挂掉的情况的逻辑：
	 * 1.选主，创建临时路径节点 （路径为/HaSimpleTask/{任务ID}/runing）成功的为主节点，节点数据为分片号，其他为从节点监听此路径节点/HaSimpleTask/{任务ID}。
	 * 2.主节点完成任务逻辑后创建永久路径节点（路径为/HaSimpleTask/{任务ID}/complete）,并删除掉临时路径节点 （路径为/HaSimpleTask/{任务ID}/runing），结束本次任务
	 * 3.其他节点得到掉临时路径节点被删除通知，先查看是否存在complete节点，存在则结束任务，否则重复第1步逻辑。
	 */
	

}
