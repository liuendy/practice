package com.ybwh.zookeeper.proto.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class SimpleWatcher implements Watcher{
	

	@Override
	public void process(WatchedEvent event) {
		
		System.out.println(event.getPath() + "  " + event.getType());
		
		
		
	}

}
