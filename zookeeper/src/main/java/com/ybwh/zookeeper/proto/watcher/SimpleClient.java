package com.ybwh.zookeeper.proto.watcher;

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
		
		ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 3000, new SimpleWatcher());
		try {
			String path = "/eee";
			byte[] data = zooKeeper.getData(path, new ReEffectiveWatcher(zooKeeper), null);
			System.out.println(data);
			
			

			//创建节点
//			List<ACL> acls = new ArrayList<ACL>();
//			acls.add(new ACL(Perms.ALL, Ids.ANYONE_ID_UNSAFE));
//			zooKeeper.create("/test/es", "".getBytes(), acls, CreateMode.PERSISTENT);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
		System.in.read();
	}
}
