package com.ybwh.zookeeper.proto;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class SimpleClient {
	public static void main(String[] args) throws IOException {
		ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 3000, new SimpleWatcher());
		
		System.in.read();
	}
}
