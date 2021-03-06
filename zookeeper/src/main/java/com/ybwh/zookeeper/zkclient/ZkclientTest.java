package com.ybwh.zookeeper.zkclient;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

public class ZkclientTest {
	
	@Test
	public void testEphemeral() throws IOException{
		/**
		 * 临时节点失效的时间与sessionTimeout有关，即客户端断开后，服务端经过sessionTimeout时间
		 * 才认为客户端断开，然后删掉临时节点
		 * */
		try {
			ZkClient zkclient = new ZkClient("localhost:2181",1000,5000);
			zkclient.createEphemeral("/eee", "112");
			
			
			System.in.read();
		} catch (ZkInterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Test
	public void testCreate() throws IOException {
		try {
			ZkClient zkclient = new ZkClient("localhost:2181", 5000);

			// create 不支持递归创建
			// zkclient.create("/usr/local", "linux install path",
			// CreateMode.PERSISTENT);
			// createPersistent支持递归创建
			zkclient.createPersistent("/usr/local", true);

			// createEphemeral 不支持递归创建
			// zkclient.createEphemeral("/uuu/iii", 344);

			// System.in.read();

			zkclient.close();
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

	@Test
	public void testDel() {

		ZkClient zkclient = new ZkClient("localhost:2181", 5000);
		try {
			// delete不支持递归删除
			// zkclient.delete("/usr/local");
			// 递归删除
			zkclient.deleteRecursive("/rooster-gather/123456789");

			System.out.println("delete success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}

	}

	@Test
	public void testGetX() {
		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		try {
			Entry<List<ACL>, Stat> acl = zkclient.getAcl("/test");
			System.out.println(acl.getValue());
			for (ACL a : acl.getKey()) {
				System.out.println(a);
			}

			List<String> children = zkclient.getChildren("/test");
			for (String s : children) {
				System.out.println(s);
			}

		} catch (ZkException e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}

	}

	@Test
	public void testReadData() {
		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		try {

			String data = zkclient.readData("/test", true);// 数据类型必须对应，不然抛异常
			System.out.println(data);

		} catch (ZkException e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}
	}

	@Test
	public void testWriteData() {
		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		try {
			zkclient.writeData("/kkk", "dfsfs333333333333");
			System.out.println("write success");

		} catch (ZkException e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}
	}

	@Test
	public void testNodeWatcher() {

		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		IZkDataListener dataListener = new IZkDataListener() {

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("handleDataChange " + dataPath + " " + data);
			}

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("handleDataDeleted " + dataPath);
			}

		};

		try {
			zkclient.subscribeDataChanges("/kkk", dataListener);
			zkclient.subscribeDataChanges("/kkk", dataListener);// 同一节点重复注册同一个对象不会触发多次
			zkclient.subscribeDataChanges("/usr", dataListener);

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			zkclient.writeData("/kkk", "33333");
			zkclient.writeData("/usr", "33333");
			// zkclient.delete("/kkk");

			try {
				Thread.sleep(200000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (ZkException e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}

	}

	@Test
	public void testCancleNodeWatcher() {

		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		try {

			IZkDataListener dataListener = new IZkDataListener() {

				@Override
				public void handleDataChange(String dataPath, Object data) throws Exception {
					System.out.println("handleDataChange " + dataPath + " " + data);
				}

				@Override
				public void handleDataDeleted(String dataPath) throws Exception {
					System.out.println("handleDataDeleted " + dataPath);
				}

			};

			// zkclient.subscribeDataChanges("/kkk", dataListener);
			zkclient.subscribeDataChanges("/usr", dataListener);

			zkclient.writeData("/kkk", "33333");
			zkclient.writeData("/usr", "33333");

			zkclient.unsubscribeDataChanges("/kkk", dataListener);

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			zkclient.writeData("/kkk", "33333");
			zkclient.writeData("/usr", "33333");
			// zkclient.delete("/kkk");

			try {
				Thread.sleep(200000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (ZkException e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}

	}

	@Test
	public void testChildrenChange() {

		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		IZkDataListener dataListener = new IZkDataListener() {

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("handleDataChange " + dataPath + " " + data);
			}

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("handleDataDeleted " + dataPath);
			}

		};

		IZkChildListener childListener = new IZkChildListener() {

			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				System.out.println("ChildChanges" + parentPath + " " + currentChilds);

				for (String c : currentChilds) {
					// System.out.println(parentPath+"/"+c);
					zkclient.subscribeDataChanges(parentPath + "/" + c, dataListener);
				}

			}
		};

		try {

			List<String> cPath = zkclient.getChildren("/kkk");
			for (String p : cPath) {
				zkclient.subscribeDataChanges("/kkk/" + p, dataListener);

			}

			zkclient.subscribeChildChanges("/kkk", childListener);

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			zkclient.writeData("/kkk/ddd", "33333");
			// zkclient.delete("/kkk");

			try {
				Thread.sleep(200000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (ZkException e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}

	}

	@Test
	public void testChildrenWatcher() {

		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		try {
			zkclient.subscribeChildChanges("/kkk", new IZkChildListener() {
				// 创建和删除子节点会触发这里
				@Override
				public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
					System.out.println("ChildChanges" + parentPath + " " + currentChilds);

				}
			});

			// zkclient.createPersistent("/kkk/eee");

			try {
				Thread.sleep(200000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (ZkException e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}

	}

	@Test
	public void testWatchChildrenWriteAddDelete() {
		
		/**
		 * 3步搞定子节点的增删改事件
		 */
		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		final String PARENT_NODE = "/kkk";
		IZkDataListener dataListener = new IZkDataListener() {

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("handleDataChange " + dataPath + " " + data);
			}

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("handleDataDeleted " + dataPath);
			}

		};

		IZkChildListener childListener = new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				System.out.println("ChildChanges" + parentPath + " " + currentChilds);
				if(null !=currentChilds){//为空则是父节点被删除
					//3.这里对每个子节点再次订阅数据变化。
					/**
					 * 由于订阅的是同一个dataListener，旧的子节点不会被触发两次，而新子节点的变化也被订阅
					 */
					for (String node : currentChilds) {
						zkclient.subscribeDataChanges(parentPath + "/" +node, dataListener);
					}
				}

			}
		}; 

		try {
			//1.先订阅每个子节点的数据变化
			List<String> childrenNodes = zkclient.getChildren(PARENT_NODE);
			if (null != childrenNodes) {

				for (String node : childrenNodes) {
					zkclient.subscribeDataChanges(PARENT_NODE+"/"+node, dataListener);
				}
			}

			//2.订阅父节点的子节点变化
			zkclient.subscribeChildChanges(PARENT_NODE, childListener);

			zkclient.createPersistent("/kkk/eee111");

			try {
				Thread.sleep(200000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (ZkException e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}
	}

}