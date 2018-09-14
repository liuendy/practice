package com.ybwh.utils;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 谷歌的LoadingCache
 *
 */
public class TestLoadingCache {
	@Test
	public void test() {
		LoadingCache<String, String> cache = CacheBuilder.newBuilder()
				//最大容量
				.maximumSize(1000)
				//写过多长时间过期
				.expireAfterWrite(30, TimeUnit.SECONDS)
				//读过多长时间过期
				.expireAfterAccess(30, TimeUnit.SECONDS)
				//多长时间后刷新
				.refreshAfterWrite(30, TimeUnit.SECONDS)
				//开启weak引用模式
				.weakKeys()
				.build(new CacheLoader<String, String>(){

					@Override
					public String load(String key) throws Exception {
						return key;
					}
					
				});
		
	}

}
