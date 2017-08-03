package com.ybwh.zookeeper.syncdata;

/**
 * @author fanbeibei
 *
 */
public interface TaskConsumer {
	
	/**
	 * 申请任务
	 * @return
	 */
	Task applyForTask();
	
	
	/**
	 * 将任务标记为已完成
	 * 
	 * @param task
	 */
	void markTaskAsCompleted(Task task);
}
