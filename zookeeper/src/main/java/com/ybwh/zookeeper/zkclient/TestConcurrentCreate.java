package com.ybwh.zookeeper.zkclient;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.junit.Test;

public class TestConcurrentCreate {
	
	@Test
	public void  testCreate() throws IOException{
		CountDownLatch latch = new CountDownLatch(1);
		ZkClient zkClient1 = new ZkClient("127.0.0.1:2181",1000,5000);
		ZkClient zkClient2 = new ZkClient("127.0.0.1:2181",1000,5000);
		ZkClient zkClient3 = new ZkClient("127.0.0.1:2181",1000,5000);
		
		
		String node = "/testC";
		
		CreateNodeTask t1 = new CreateNodeTask("t1",node, latch, zkClient1);
		CreateNodeTask t3 = new CreateNodeTask("t2",node, latch, zkClient3);
		CreateNodeTask t2 = new CreateNodeTask("t3",node, latch, zkClient2);
		
		new Thread(t1).start();
		new Thread(t2).start();
		new Thread(t3).start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		latch.countDown();
		
		System.in.read();
		
	}

}


class CreateNodeTask implements  Runnable{
	private String name;
	private String node;
	private CountDownLatch latch;
	private ZkClient zkClient;
	
	public CreateNodeTask(String name,String node, CountDownLatch latch,ZkClient zkClient) {
		this.name= name;
		this.node = node;
		this.latch = latch;
		this.zkClient = zkClient;
	}
	
	@Override
	public void run() {
		try {
			System.out.println(name+" start!");
			
			latch.await();
			zkClient.createEphemeral(node);
//			zkClient.createPersistent(node);
			
			System.out.println(name+" create success");
			
		} catch (ZkNodeExistsException e) {
//			e.printStackTrace();
			System.out.println(name+" create failed");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
}