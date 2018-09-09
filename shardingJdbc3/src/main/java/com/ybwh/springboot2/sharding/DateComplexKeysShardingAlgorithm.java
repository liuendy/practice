package com.ybwh.springboot2.sharding;

import java.util.Arrays;
import java.util.Collection;

import com.alibaba.fastjson.JSON;

import io.shardingsphere.core.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

public class DateComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm {
	
	private static final String TABLE_NAME = "t_report";
	private  static final String COLUMN_NAME ="create_time";

	@Override
	public Collection<String> doSharding(Collection<String> collection, Collection<ShardingValue> shardingValues) {

		System.out.println(
				"collection:" + JSON.toJSONString(collection) + ",shardingValues:" + JSON.toJSONString(shardingValues));



		return Arrays.asList("t_report201809");
	}


}
