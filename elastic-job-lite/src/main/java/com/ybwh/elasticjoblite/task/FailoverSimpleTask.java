package com.ybwh.elasticjoblite.task;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 失效转移任务。所谓失效转移，就是在执行任务的过程中遇见异常的情况，这个分片任务可以在其他节点再次执行。
 * 1.需要配置俩个参数为true:failover（默认值为false）和monitorExecution（默认值是true）
 * 2.需要自身保证幂等性
 *
 */
public class FailoverSimpleTask  implements SimpleJob {
	private static final Logger LOGGER = Logger.getLogger(FailoverSimpleTask.class);

	@Override
	public void execute(ShardingContext shardingContext) {//需要保证幂等性
		LOGGER.info("失效转移任务开始,pid="+getProcessID());
		
		LOGGER.info("失效转移任务结束,pid="+getProcessID());
	}
	
	
	public static final int getProcessID() { 
	    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	    return Integer.valueOf(runtimeMXBean.getName().split("@")[0]) 
	        .intValue(); 
	  } 


}
