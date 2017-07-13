package com.ybwh.zookeeper.curator;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

public class CuratorTest {
	@Test
	public void testCreate() {
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",
				new ExponentialBackoffRetry(100, 0));

		try {
			client.start();
			// 增
			String result = client.create().creatingParentsIfNeeded() // 递归创建父节点
					.withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
					.forPath("/kkk", "hello, zk".getBytes());
			
			
			System.out.println("create success,path=" + result);

		} catch (org.apache.zookeeper.KeeperException.NodeExistsException e) {
			System.out.println("path is exist!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			if (null != client) {
				client.close();
			}
		}

	}
	@Test
	public void testDel() {
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",
				new ExponentialBackoffRetry(1000, 3));

		try {
			client.start();

			// 删
			if (null != client.checkExists().forPath("/kkk")) {
				client.delete().deletingChildrenIfNeeded() // 递归删除 2.x中低版本不支持
						.forPath("/kkk");
				System.out.println("delete success");
			}

			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != client) {
				client.close();
			}
		}

	}
	@Test
	public void testGet() {
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",
				new ExponentialBackoffRetry(1000, 3));

		try {
			client.start();

			// 查
			byte[] data = client.getData().forPath("/usr");
			System.out.println("&&&" + new String(data));
			List<String> children = client.getChildren().forPath("/usr");
			for (String c : children) {
				System.out.println(c);
			}

			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != client) {
				client.close();
			}
		}

	}
	@Test
	public void testUpdate() {
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",
				new ExponentialBackoffRetry(1000, 3));
	
		try {
			client.start();
	
			// 改
			Stat stat = client.setData().forPath("/usr", "3333333333".getBytes());
			
			System.out.println("setData success,nodestate="+stat);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != client) {
				client.close();
			}
		}
	
	}


	@Test
	public void testWatcherSelf() {
		RetryPolicy policy = new ExponentialBackoffRetry(1000, 10);
		CuratorFramework curator = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
				.sessionTimeoutMs(2000).retryPolicy(policy).build();
		curator.start();

		try {
			NodeCache cache = new NodeCache(curator, "/super", false);
			cache.start(true);
			// 只会监听节点的创建和修改，删除不会监听
			cache.getListenable().addListener(() -> {
				System.out.println("路径：" + cache.getCurrentData().getPath());
				System.out.println("数据：" + new String(cache.getCurrentData().getData()));
				System.out.println("状态：" + cache.getCurrentData().getStat());
			});

			curator.create().forPath("/super", "1234".getBytes());
			Thread.sleep(1000);
			curator.setData().forPath("/super", "5678".getBytes());
			Thread.sleep(1000);
			curator.delete().forPath("/super");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			curator.close();
		}

	}

	@Test
	public void testWatcherChildren() {
		RetryPolicy policy = new ExponentialBackoffRetry(1000, 10);
		CuratorFramework curator = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
				.sessionTimeoutMs(2000).retryPolicy(policy).build();
		curator.start();
		try {
			// 第三个参数表示是否接收节点数据内容
			PathChildrenCache childrenCache = new PathChildrenCache(curator, "/super", true);
			/**
			 * 如果不填写这个参数，则无法监听到子节点的数据更新
			 * 如果参数为PathChildrenCache.StartMode.BUILD_INITIAL_CACHE，则会预先创建之前指定的/
			 * super节点 如果参数为PathChildrenCache.StartMode.POST_INITIALIZED_EVENT，
			 * 效果与BUILD_INITIAL_CACHE相同，只是不会预先创建/super节点
			 * 参数为PathChildrenCache.StartMode.NORMAL时，与不填写参数是同样的效果，
			 * 不会监听子节点的数据更新操作
			 */
			childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
			childrenCache.getListenable().addListener((framework, event) -> {
				switch (event.getType()) {
				case CHILD_ADDED:
					System.out.println("CHILD_ADDED，类型：" + event.getType() + "，路径：" + event.getData().getPath() + "，数据："
							+ new String(event.getData().getData()) + "，状态：" + event.getData().getStat());
					break;
				case CHILD_UPDATED:
					System.out.println("CHILD_UPDATED，类型：" + event.getType() + "，路径：" + event.getData().getPath()
							+ "，数据：" + new String(event.getData().getData()) + "，状态：" + event.getData().getStat());
					break;
				case CHILD_REMOVED:
					System.out.println("CHILD_REMOVED，类型：" + event.getType() + "，路径：" + event.getData().getPath()
							+ "，数据：" + new String(event.getData().getData()) + "，状态：" + event.getData().getStat());
					break;
				default:
					break;
				}
			});

			curator.create().forPath("/super", "123".getBytes());
			curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c1",
					"c1内容".getBytes());
			// 经测试，不会监听到本节点的数据变更，只会监听到指定节点下子节点数据的变更
			curator.setData().forPath("/super", "456".getBytes());
			curator.setData().forPath("/super/c1", "c1新内容".getBytes());
			curator.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		curator.close();
	}

	@Test
	public void testWatchBoth() {
		RetryPolicy policy = new ExponentialBackoffRetry(1000, 10);
		CuratorFramework curator = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
				.sessionTimeoutMs(2000).retryPolicy(policy).build();
		curator.start();
		try {
			TreeCache treeCache = new TreeCache(curator, "/treeCache");
			treeCache.start();
			treeCache.getListenable().addListener((curatorFramework, treeCacheEvent) -> {
				switch (treeCacheEvent.getType()) {
				case NODE_ADDED:
					System.out.println("NODE_ADDED：路径：" + treeCacheEvent.getData().getPath() + "，数据："
							+ new String(treeCacheEvent.getData().getData()) + "，状态："
							+ treeCacheEvent.getData().getStat());
					break;
				case NODE_UPDATED:
					System.out.println("NODE_UPDATED：路径：" + treeCacheEvent.getData().getPath() + "，数据："
							+ new String(treeCacheEvent.getData().getData()) + "，状态："
							+ treeCacheEvent.getData().getStat());
					break;
				case NODE_REMOVED:
					System.out.println("NODE_REMOVED：路径：" + treeCacheEvent.getData().getPath() + "，数据："
							+ new String(treeCacheEvent.getData().getData()) + "，状态："
							+ treeCacheEvent.getData().getStat());
					break;
				default:
					break;
				}
			});

			curator.create().forPath("/treeCache", "123".getBytes());
			curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/treeCache/c1",
					"456".getBytes());
			curator.setData().forPath("/treeCache", "789".getBytes());
			curator.setData().forPath("/treeCache/c1", "910".getBytes());
			curator.delete().forPath("/treeCache/c1");
			curator.delete().forPath("/treeCache");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		curator.close();
	}

}
