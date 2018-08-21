package linkedhashmap;

import java.util.LinkedHashMap;

/**
 * LRU:最近最少使用算法。在这种算法中避免了上面的问题，每次访问数据都会将其放在我们的队尾，
 * 如果需要淘汰数据，就只需要淘汰队首即可。但是这个依然有个问题，如果有个数据在1个小时的前59分钟访问了1万次(可见这是个热点数据),
 * 再后一分钟没有访问这个数据，但是有其他的数据访问，就导致了我们这个热点数据被淘汰。
 * 
 * 也可以用guava的LoadingCache实现LRU
 *
 * @param <K>
 * @param <V>
 */
public class LRUMap<K, V> extends LinkedHashMap<K, V> {
	private final int max;

	private Object lock;

	public LRUMap(int max, Object lock) {
		super((int) (max * 1.4F), 0.75F, true);
		this.max = max;
		this.lock = lock;
	}

	
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		/**
		 * 重写LinkedHashMap的removeEldestEntry方法在Put时候判断，如果为true就删除最老的
		 */
		return size() > max;
	}

	public V getValue(K key) {
		synchronized (lock) {
			return get(key);
		}
	}

	public void putValue(K key, V value) {
		synchronized (lock) {
			put(key, value);
		}
	}

	public boolean removeValue(K key) {
		synchronized (lock) {
			return remove(key) != null;
		}
	}
	
	
	public boolean removeAll() {
		clear();
		return true;
	}
	
	
}
