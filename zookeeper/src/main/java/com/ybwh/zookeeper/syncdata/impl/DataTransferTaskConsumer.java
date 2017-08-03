package com.ybwh.zookeeper.syncdata.impl;


import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.AsyncCallback.Children2Callback;

import com.ybwh.zookeeper.syncdata.Task;
import com.ybwh.zookeeper.syncdata.TaskConsumer;

public class DataTransferTaskConsumer implements TaskConsumer{
	private ZkClient zk;
	
	public DataTransferTaskConsumer(String zkServer) {
		zk = new ZkClient(zkServer, 1000, 5000);
		init();
	}
	
	public void init(){
		if(!zk.exists(Constant.TASK_NODE_ROOT)){
		
			try {
				zk.createPersistent(Constant.TASK_NODE_ROOT,true);
			} catch (ZkNodeExistsException e) {
			}
		}
	}
	

	@Override
	public Task applyForTask() {
		/**
		 * 在任务节点（永久节点）下创建一个名称为@Code{EXE_NODE_NAME}临时节点，
		 * 创建成功则申请任务成功，创建失败则
		 */
		List<String> children = zk.getChildren(Constant.TASK_NODE_ROOT);
		if (null != children  && children.size()>0) {
			for (String c : children) {
				
				
			}
		}
		
		
		
		return null;
	}

	@Override
	public void markTaskAsCompleted(Task task) {
		// TODO Auto-generated method stub
		
	}

	

}
