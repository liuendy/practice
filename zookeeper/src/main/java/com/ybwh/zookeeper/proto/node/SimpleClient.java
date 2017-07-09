package com.ybwh.zookeeper.proto.node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

public class SimpleClient {
	public static void main(String[] args) throws IOException {
		
		ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 3000, null);
		try {
			//创建节点
//			List<ACL> acls = new ArrayList<ACL>();
//			acls.add(new ACL(Perms.ALL, Ids.ANYONE_ID_UNSAFE));
//			zooKeeper.create("/test/es", "2222".getBytes(), acls, CreateMode.PERSISTENT);
			
			
			
			String path = "/test/es";
			byte[] data = zooKeeper.getData(path, null, null);
			System.out.println(new String(data));
			
			

			
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
		System.in.read();
	}
}
