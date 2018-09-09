package com.ybwh.springboot2.sharding;

import java.sql.Date;
import java.util.Collection;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

public class DatePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {

		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + availableTargetNames);
		System.out.println("###################################" + shardingValue);

		return "t_report_201809";
	}
}
