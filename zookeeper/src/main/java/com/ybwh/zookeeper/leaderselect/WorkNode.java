package com.ybwh.zookeeper.leaderselect;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

public class WorkNode {
	private static final String  MASTER_NODE = "/master";
	
	/**
	 * 0为master，非0为slaver
	 */
	private int state = -1;
	
	private int nodeId;
	
	private int masterId;
	
	private ZkClient zkClient;
	
	private IZkDataListener dataListener = new IZkDataListener() {

		@Override
		public void handleDataChange(String dataPath, Object data) throws Exception {
			
		}

		@Override
		public void handleDataDeleted(String dataPath) throws Exception {
			applyBecomeMaster();
		}

	};
	
	public WorkNode(int nodeId,ZkClient zkClient){
		this.nodeId = nodeId;
		this.zkClient = zkClient;
	}
	
	protected void applyBecomeMaster(){
		try {
//			zkClient.createEphemeral(MASTER_NODE);
//			zkClient.writeData(MASTER_NODE, nodeId);
			zkClient.create(MASTER_NODE, nodeId, CreateMode.EPHEMERAL);
			state = 0;
			masterId = nodeId;
			System.out.println("node"+nodeId+" become master");
		} catch (ZkNodeExistsException e) {
			zkClient.subscribeDataChanges(MASTER_NODE, dataListener);
			masterId = zkClient.readData(MASTER_NODE);
			System.out.println("node"+nodeId+" become slaver");
		}
	
	}
	
	
	public void start(){
		applyBecomeMaster();
	}
	

}
