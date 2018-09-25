package com.ybwh.springboot2.sharding.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.google.common.collect.Range;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

public class DateStandardShardingAlgorithm implements PreciseShardingAlgorithm<Date> ,RangeShardingAlgorithm<Date>{
	private static final DateFormat YYYY_MM = new SimpleDateFormat("yyyyMM");

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {


		return shardingValue.getLogicTableName() + YYYY_MM.format(shardingValue.getValue());
	}

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
			RangeShardingValue<Date> shardingValue) {
		
		Collection<String> result = new HashSet<>();
		Range<Date> ranges = shardingValue.getValueRange();
		Date startTime = ranges.lowerEndpoint();
		Date endTime = ranges.upperEndpoint();
		// 此处应该返回 tablePrefix+201808 , tablePrefix+201809,tablePrefix+201810,
		Calendar cal = Calendar.getInstance();
		
		
		final String logicTableName = shardingValue.getLogicTableName();

		while (startTime.getTime() <= endTime.getTime()) {
			result.add(logicTableName + YYYY_MM.format(startTime));
			cal.setTime(startTime);// 设置起时间
			cal.add(Calendar.MONTH, 1);
			startTime = cal.getTime();
		}
		return result;
	}
}
