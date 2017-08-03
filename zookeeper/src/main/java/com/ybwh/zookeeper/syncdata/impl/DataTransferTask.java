package com.ybwh.zookeeper.syncdata.impl;

import com.ybwh.zookeeper.syncdata.Task;

/**
 * 数据同步任务
 * 
 * @author fanbeibei
 *
 */
public class DataTransferTask implements Task,Runnable{

	@Override
	public void run() {
		doTask();
	}

	@Override
	public void doTask() {
		
		
	}

}
