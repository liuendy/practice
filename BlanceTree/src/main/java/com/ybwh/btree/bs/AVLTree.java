package com.ybwh.btree.bs;

/**
 * 平衡二叉搜索树
 *
 */
public class AVLTree <K extends Comparable<K>, V>{
	/**
	 * 二叉搜索树的节点
	 *
	 */
	@SuppressWarnings("hiding")
	private class Node<K extends Comparable<K>, V> {
		Node<K, V> parent;
		Node<K, V> left;
		Node<K, V> right;
		K key;
		V data;

		public Node(K key, V data) {
			this.key = key;
			this.data = data;
		}
	}

	/**
	 * 根节点
	 */
	private Node<K, V> root;
	
	
	/**
	 * 插入数据
	 * 
	 * @param key
	 * @param data
	 */
	public void insert(K key, V data) {
		Node<K, V> x = root;
		Node<K, V> y = null;
		Node<K, V> node = new Node<K, V>(key, data);

		while (null != x) {
			y = x;
			/**
			 * key小于x的key说明应该将node插入到x的左子树中，否则插入到右子树中
			 */
			if(key.compareTo(x.key) < 0) {
				x = x.left;
			}else {
				x = x.right;
			}
		}
		
		node.parent = y;
		//非空树情况
		if(null != y) {
			if(key.compareTo(y.key) < 0) {
				y.left = node;
			}else {
				y.right = node;
			}
		}else {
			root = node;
		}
		
		keepBalance(node);
		
	}

	/**
	 * 搜索二叉树节点
	 * 
	 * @param key
	 *            特征值
	 * @return
	 */
	public V search(K key) {
		Node<K, V> x = root;
		while(null != x) {
			if(key.compareTo(x.key)< 0 ) {
				x = x.left;
			}else if(key.compareTo(x.key)> 0 ){
				x = x.right;
			}else {
				return x.data;
			}
			
		}
		
		return null;
	}

	/**
	 * 删除节点
	 * 
	 * @param key
	 *            特征值
	 */
	public void delete(K key) {
		Node<K, V> x = root;
		while(null != x) {
			if(key.compareTo(x.key)< 0 ) {
				x = x.left;
			}else if(key.compareTo(x.key)> 0 ){
				x = x.right;
			}else {
				delete(x);
			}
			
		}
		
	}

	/**
	 * 删除节点
	 * 
	 * ① 被删除节点没有儿子，即为叶节点。那么，直接将该节点删除就OK了。
	   ② 被删除节点只有一个儿子。那么，直接删除该节点，并用该节点的唯一子节点顶替它的位置。
	   ③ 被删除节点有两个儿子。那么，找出此结点右子树中的最小结点，用以代替要删除的结点，然后删除此最小结点.
	 * 
	 * @param node 被删除的节点
	 */
	private void delete(Node<K, V> node) {
		//被删除节点左右子节点都不为空
		if(null != node.left && null != node.right) {
			Node<K, V> right = node.right;
			node.key = right.key;
			node.data = right.data;
			
			//获取右子树中的最小结点
			Node<K, V> rMin = node.right;
	        while (rMin.left != null) {
	        	rMin = rMin.left;
	        }
	        
	        //node不是根节点
	        if(null != node.parent) {
	        	//用右子树中的最小结点的key和data覆盖node
	        	node.key = rMin.key;
	        	node.data = rMin.data;
	        	//删除最小节点
	        	if(rMin == rMin.parent.left) {
	        		rMin.parent.left = null;
	        	}else {
	        		rMin.parent.right = null;
	        	}
	        }else {
	        	root = rMin;
	        }
	        
	        
	        
	        
	        return ;
		}
		
		
		Node<K, V> child = null;
		//被删除节点左子节点都不为空
		if(null != node.left) {
			child = node.left;
			
		}else if(null != node.right){
			//被删除节点右子节点都不为空
			child = node.right;
		}
		
		 //node不是根节点
        if(null != node.parent) {
        	//node 是父节点的 左子节点
        	if(node == node.parent.left) {
        		node.parent.left = child;
        	}else {
        		//node 是父节点的 右子节点
        		node.parent.right = child;
        	}
        }else {
        	root = child;
        }
        
	}
	
	
	/**
	 * 使得子树保持平衡
	 * 
	 * @param node  子树的根
	 */
	private void keepBalance(Node<K, V> node) {
		
		
	}
}
