package com.ybwh.concurrent.daemon;

/**
 * (一)Main线程是个非守护线程，不能设置成守护线程。
 * （二）Main线程结束，其他线程一样可以正常运行。 
 * (三)Main线程结束，其他线程也可以立刻结束，当且仅当这些子线程都是守护线程。
 *
 */
public class TestMainThread {
	/**
	 *Main线程是个非守护线程，不能设置成守护线程。
	 */
	public static void main(String[] args)  
    {  
        System.out.println(" parent thread begin ");  
        Thread.currentThread().setDaemon(true);  
    }  
}
