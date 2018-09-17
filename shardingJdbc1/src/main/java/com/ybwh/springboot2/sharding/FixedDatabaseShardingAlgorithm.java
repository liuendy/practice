package com.ybwh.springboot2.sharding;

import java.util.Arrays;
import java.util.Collection;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;

/**
 * 
 *
 */
public class FixedDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm{
	
	private String dsName;
	

	public FixedDatabaseShardingAlgorithm(String dsName) {
		this.dsName = dsName;
	}

	@Override
	public String doEqualSharding(Collection availableTargetNames, ShardingValue shardingValue) {
		return dsName;
	}

	@Override
	public Collection doInSharding(Collection availableTargetNames, ShardingValue shardingValue) {
		return Arrays.asList(dsName);
	}

	@Override
	public Collection doBetweenSharding(Collection availableTargetNames, ShardingValue shardingValue) {
		return Arrays.asList(dsName);
	}

}
