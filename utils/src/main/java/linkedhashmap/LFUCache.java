package linkedhashmap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * LFU cache implementation based on http://dhruvbird.com/lfu.pdf, with some
 * notable differences:
 * <ul>
 * <li>Frequency list is stored as an array with no next/prev pointers between
 * nodes: looping over the array should be faster and more CPU-cache friendly
 * than using an ad-hoc linked-pointers structure.</li>
 * <li>The max frequency is capped at the cache size to avoid creating more and
 * more frequency list entries, and all elements residing in the max frequency
 * entry are re-positioned in the frequency entry linked set in order to put
 * most recently accessed elements ahead of less recently ones, which will be
 * collected sooner.</li>
 * <li>The eviction factor determines how many elements (more specifically, the
 * percentage of) will be evicted.</li>
 * </ul>
 * As a consequence, this cache runs in *amortized* O(1) time (considering the
 * worst case of having the lowest frequency at 0 and having to evict all
 * elements).
 *
 * @author Sergio Bossa
 * 
 * 这是activemq的LFU实现
 * 我认为 frequencyList 应该用TreeMap实现更为妥当
 * 
 * 由于该实现算法的时间复杂度为O(1),故并发时直接加锁来同步即可
 */
public class LFUCache<K, V> implements Map<K, V> {

	private final Map<K, CacheNode<K, V>> cache;
	private final LinkedHashSet<CacheNode<K, V>>[] frequencyList;
	/**
	 * 最低频率的LinkedHashSet下标
	 */
	private int lowestFrequency;

	/**
	 * 最高频率的LinkedHashSet下标
	 */
	private int maxFrequency;
	//
	/**
	 * 缓存最大容量
	 */
	private final int maxCacheSize;
	/**
	 * 淘汰因子，表示最多淘汰的数量占比
	 */
	private final float evictionFactor;

	public LFUCache(int maxCacheSize, float evictionFactor) {
		if (evictionFactor <= 0 || evictionFactor >= 1) {
			throw new IllegalArgumentException("Eviction factor must be greater than 0 and lesser than or equal to 1");
		}
		this.cache = new HashMap<K, CacheNode<K, V>>(maxCacheSize);
		this.frequencyList = new LinkedHashSet[maxCacheSize];
		this.lowestFrequency = 0;
		this.maxFrequency = maxCacheSize - 1;
		this.maxCacheSize = maxCacheSize;
		this.evictionFactor = evictionFactor;
		initFrequencyList();
	}

	@Override
	public V put(K key, V value) {
		V oldValue = null;
		CacheNode<K, V> currentNode = cache.get(key);
		if (currentNode == null) {
			if (cache.size() >= maxCacheSize) {
				doEviction();
			}
			LinkedHashSet<CacheNode<K, V>> nodes = frequencyList[0];
			currentNode = new CacheNode<K, V>(key, value, 0);
			nodes.add(currentNode);
			cache.put(key, currentNode);
			lowestFrequency = 0;
		} else {
			oldValue = currentNode.value;
			currentNode.value = value;
		}
		return oldValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> me : map.entrySet()) {
			put(me.getKey(), me.getValue());
		}
	}

	@Override
	public V get(Object key) {
		CacheNode<K, V> currentNode = cache.get(key);
		if (null == currentNode) {
			return null;
		}

		int currentFrequency = currentNode.frequency;
		if (currentFrequency < maxFrequency) {
			int nextFrequency = currentFrequency + 1;
			LinkedHashSet<CacheNode<K, V>> currentNodes = frequencyList[currentFrequency];
			LinkedHashSet<CacheNode<K, V>> newNodes = frequencyList[nextFrequency];
			moveToNextFrequency(currentNode, nextFrequency, currentNodes, newNodes);
			cache.put((K) key, currentNode);
			if (lowestFrequency == currentFrequency && currentNodes.isEmpty()) {
				lowestFrequency = nextFrequency;
			}
		} else {
			// 访问频率大于maxFrequency均存在数组尾部
			// Hybrid with LRU: put most recently accessed ahead of others:
			LinkedHashSet<CacheNode<K, V>> nodes = frequencyList[currentFrequency];
			nodes.remove(currentNode);
			nodes.add(currentNode);
		}

		return currentNode.value;
	}

	@Override
	public V remove(Object k) {
		CacheNode<K, V> currentNode = cache.remove(k);
		if (currentNode != null) {
			LinkedHashSet<CacheNode<K, V>> nodes = frequencyList[currentNode.frequency];
			nodes.remove(currentNode);
			if (lowestFrequency == currentNode.frequency) {
				findNextLowestFrequency();
			}
			return currentNode.value;
		} else {
			return null;
		}
	}

	public int frequencyOf(K k) {
		CacheNode<K, V> node = cache.get(k);
		if (node != null) {
			return node.frequency + 1;
		} else {
			return 0;
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i <= maxFrequency; i++) {
			frequencyList[i].clear();
		}
		cache.clear();
		lowestFrequency = 0;
	}

	@Override
	public Set<K> keySet() {
		return this.cache.keySet();
	}

	@Override
	public Collection<V> values() {
		return null; // To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return null; // To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int size() {
		return cache.size();
	}

	@Override
	public boolean isEmpty() {
		return this.cache.isEmpty();
	}

	@Override
	public boolean containsKey(Object o) {
		return this.cache.containsKey(o);
	}

	@Override
	public boolean containsValue(Object o) {
		return false; // To change body of implemented methods use File | Settings | File Templates.
	}

	private void initFrequencyList() {
		for (int i = 0; i <= maxFrequency; i++) {
			frequencyList[i] = new LinkedHashSet<CacheNode<K, V>>();
		}
	}

	private void doEviction() {
		int currentlyDeleted = 0;
		float target = maxCacheSize * evictionFactor;
		while (currentlyDeleted < target) {
			LinkedHashSet<CacheNode<K, V>> nodes = frequencyList[lowestFrequency];
			// 由于是缓存满了后才淘汰，故这里异常不会抛出，除非代码有bug
			if (nodes.isEmpty()) {
				throw new IllegalStateException("Lowest frequency constraint violated!");
			} else {
				Iterator<CacheNode<K, V>> it = nodes.iterator();
				while (it.hasNext() && currentlyDeleted++ < target) {
					CacheNode<K, V> node = it.next();
					it.remove();
					cache.remove(node.key);
				}

				// 删完一个还没达到要删除的数量
				if (!it.hasNext()) {
					findNextLowestFrequency();
				}
			}
		}
	}

	private void moveToNextFrequency(CacheNode<K, V> currentNode, int nextFrequency,
			LinkedHashSet<CacheNode<K, V>> currentNodes, LinkedHashSet<CacheNode<K, V>> newNodes) {
		currentNodes.remove(currentNode);
		newNodes.add(currentNode);
		currentNode.frequency = nextFrequency;
	}

	private void findNextLowestFrequency() {
		while (lowestFrequency <= maxFrequency && frequencyList[lowestFrequency].isEmpty()) {
			lowestFrequency++;
		}
		if (lowestFrequency > maxFrequency) {
			lowestFrequency = 0;
		}
	}

	private static class CacheNode<K, V> {

		final K key;
		V value;
		int frequency;

		CacheNode(K key, V value, int frequency) {
			this.key = key;
			this.value = value;
			this.frequency = frequency;
		}

	}
}