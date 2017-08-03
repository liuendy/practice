package com.ybwh.zookeeper.syncdata.impl;

public interface Constant {
	
	/**
	 * 任务节点所在的根节点
	 */
	String TASK_NODE_ROOT = "/ybwh/task";
	
	/**
	 * 任务节点下标识正在执行状态的临时节点名称
	 */
	String EXE_INFO_NODE = "inExec";

}
