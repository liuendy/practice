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
			zkclient.delete("/usr/local");
			// 递归删除
			zkclient.deleteRecursive("/usr");

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
	public void testReadData(){
		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		try {
			
			String data =zkclient.readData("/test", true);//数据类型必须对应，不然抛异常
			System.out.println(data);
			
		} catch (ZkException e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}
	}
	
	@Test
	public void testWriteData(){
		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		try {
			zkclient.writeData("/test", "dfsfs333333333333");
			System.out.println("write success");
			
		} catch (ZkException e) {
			e.printStackTrace();
		} finally {
			zkclient.close();
		}
	}
	
	@Test
	public void testNodeWatcher(){
		
		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		try {
			zkclient.subscribeDataChanges("/kkk", new IZkDataListener(){

				@Override
				public void handleDataChange(String dataPath, Object data) throws Exception {
					System.out.println("handleDataChange "+dataPath+" "+data);
				}

				@Override
				public void handleDataDeleted(String dataPath) throws Exception {
					System.out.println("handleDataDeleted "+dataPath);
				}
				
			});
			
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			zkclient.writeData("/kkk", "33333");
//			zkclient.delete("/kkk");
			
			
			
			
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
	public void testChildrenWatcher(){
		
		ZkClient zkclient = new ZkClient("localhost:2181", 5000);

		try {
			zkclient.subscribeChildChanges("/kkk", new IZkChildListener() {
				//创建和删除子节点会触发这里
				@Override
				public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
					System.out.println("ChildChanges"+parentPath+" "+currentChilds);
					
				}
			});
			

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