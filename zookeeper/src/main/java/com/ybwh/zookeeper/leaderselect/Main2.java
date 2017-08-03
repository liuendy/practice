package com.ybwh.zookeeper.leaderselect;

import java.io.IOException;

import org.I0Itec.zkclient.ZkClient;

public class Main2 {
	public static void main(String[] args) throws IOException {
		
		ZkClient zkClient = new ZkClient("127.0.0.1:2181",1000,5000);
		WorkNode node1 = new WorkNode(2, zkClient);
		
		node1.start();
		
		System.in.read();
	}

}
