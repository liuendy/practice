package com.ybwh.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

public class ZkclientTest {
	
	@Test
	public void testCreate(){
		try {
			ZkClient zkclient = new ZkClient("localhost:2181", 5000);
//			zkclient.create("/usr/local", "linux install path", CreateMode.PERSISTENT);
			zkclient.createPersistent("/usr/local", true);
		} catch (ZkInterruptedException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ZkException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	
	}

	
	
	
	

}