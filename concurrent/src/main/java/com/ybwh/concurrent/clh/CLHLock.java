package com.ybwh.concurrent.clh;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * CLH队列中的结点QNode中含有一个locked字段，该字段若为true表示该线程需要获取锁，且不释放锁，
 * 为false表示线程释放了锁。结点之间是通过隐形的链表相连，之所以叫隐形的链表是因为这些结点之间没有明显的next指针，
 * 而是通过myPred所指向的结点的变化情况来影响myNode的行为。CLHLock上还有一个尾指针，始终指向队列的最后一个结点.
 * </p>
 * <p>
 * 当一个线程需要获取锁时，会创建一个新的QNode，将其中的locked设置为true表示需要获取锁，
 * 然后线程对tail域调用getAndSet方法，使自己成为队列的尾部，同时获取一个指向其前趋的引用myPred,
 * 然后该线程就在前趋结点的locked字段上旋转，直到前趋结点释放锁。当一个线程需要释放锁时，
 * 将当前结点的locked域设置为false，同时回收前趋结点。</P>
 * 
 * @author fanbeibei
 *
 */
public class CLHLock {

	AtomicReference<QNode> tail = new AtomicReference<QNode>(new QNode());
	ThreadLocal<QNode> myPred;
	ThreadLocal<QNode> myNode;

	public static class QNode {
		// 注意这个地方 如果不加volatile则会导致线程永远死循环
		public volatile boolean locked = false;
	}

	public CLHLock() {
		
		myNode = new ThreadLocal<QNode>() {
			protected QNode initialValue() {
				return new QNode();
			}
		};
		
		myPred = new ThreadLocal<QNode>() {
			protected QNode initialValue() {
				return null;
			}
		};
		
	}

	public void lock() {
		
		QNode qnode = myNode.get();
		qnode.locked = true;
		QNode pred = tail.getAndSet(qnode);
		myPred.set(pred);
		while (pred.locked) {
			// 非阻塞算法
		}
		
	}

	public void unlock() {
		QNode qnode = myNode.get();
		qnode.locked = false;
		myNode.set(myPred.get());
	}
}