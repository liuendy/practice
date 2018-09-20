package com.ybwh.springboot1.sharding;

import java.util.Arrays;
import java.util.Collection;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;



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
