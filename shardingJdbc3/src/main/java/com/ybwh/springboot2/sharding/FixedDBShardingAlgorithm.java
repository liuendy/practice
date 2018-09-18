package com.ybwh.springboot2.sharding;

import java.util.Arrays;
import java.util.Collection;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

/**
 * 不分库(返回固定数据源)策略
 *
 * @param <T>
 */
public class FixedDBShardingAlgorithm<T extends Comparable<?>>
		implements RangeShardingAlgorithm<Comparable<?>>, PreciseShardingAlgorithm<Comparable<?>> {

	private String fixedDb;

	public FixedDBShardingAlgorithm(String fixedDb) {
		this.fixedDb = fixedDb;
	}

	@Override
	public String doSharding(Collection<String> availableTargetNames,
			PreciseShardingValue<Comparable<?>> shardingValue) {
		return fixedDb;
	}

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
			RangeShardingValue<Comparable<?>> shardingValue) {
		return Arrays.asList(fixedDb);
	}

}
