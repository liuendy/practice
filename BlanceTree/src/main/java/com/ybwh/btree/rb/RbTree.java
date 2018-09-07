package com.ybwh.btree.rb;

/**
 * 红黑树
 *
 * @param <K>
 *            特征值类型
 * @param <V>
 *            携带的数据类型
 */
public class RbTree<K extends Comparable<K>, V>{

	private class Node<K, V> {
		static final boolean RED = true;
		static final boolean BLACK = true;

		Node<K, V> parent;
		Node<K, V> left;
		Node<K, V> right;
		K key;
		V data;
		boolean color;

		public Node(K key, V data) {
			this.key = key;
			this.data = data;
		}
	}

	private Node<K, V> root;

	public void insert(K key, V data) {

	}

	public void delete(K key) {

	}

	public Node<K, V> search(K key) {
		return null;
	}

	public boolean empty() {
		return false;
	}

	public void display() {

	}

	/**
	 * 左旋
	 * 
	 * @param xNode
	 */
	private void leftRotate(Node<K, V> xNode) {
		Node<K, V> yNode = xNode.right;

		// 1. 补,分离右子节点
		xNode.right = yNode.left;
		if (null != yNode.left) {
			yNode.left.parent = xNode;
		}

		// 2. 提,交换parent
		yNode.parent = xNode.parent;
		if (null == xNode.parent) {
			root = yNode;
		}

		// 3. 降 ,交换left与right
		yNode.left = xNode;
		xNode.parent = yNode;

	}

	/**
	 * 右旋
	 * 
	 * @param xNode
	 */
	private void rightRotate(Node<K, V> xNode) {

	}

}
