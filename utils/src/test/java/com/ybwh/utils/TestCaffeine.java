package com.ybwh.utils;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * Caffeine缓存工具
 *
 */
public class TestCaffeine {
	@Test
	public void test() {
		LoadingCache<String, String> graphs = Caffeine.newBuilder()
			    .maximumSize(10_000)
			    .expireAfterWrite(5, TimeUnit.MINUTES)
			    .refreshAfterWrite(1, TimeUnit.MINUTES)
			    .build(new CacheLoader<String, String>() {

					@Override
					public String load(String key) throws Exception {
						return key;
					}
				});
		
	}

}
