package com.ybwh.springboot2.sharding;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import io.shardingsphere.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

public class DateRangeShardingAlgorithm implements RangeShardingAlgorithm<Date>{

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
			RangeShardingValue<Date> shardingValue) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + availableTargetNames);
		System.out.println("###################################" + shardingValue);
		return Arrays.asList("t_report_201809");
	}

}
