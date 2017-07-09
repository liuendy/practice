package com.ybwh.zookeeper.proto.watcher;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

public class ReEffectiveWatcher implements Watcher {

	private ZooKeeper zooKeeper;

	public ReEffectiveWatcher(ZooKeeper zooKeeper) {
		this.zooKeeper = zooKeeper;
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println(event.getPath() + "  " + event.getType());

		String path = event.getPath();
		EventType eventType = event.getType();

		if (!EventType.NodeDeleted.equals(eventType)) {
			// 重新注册
			try {
				zooKeeper.getData(path, new ReEffectiveWatcher(zooKeeper), null);
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
