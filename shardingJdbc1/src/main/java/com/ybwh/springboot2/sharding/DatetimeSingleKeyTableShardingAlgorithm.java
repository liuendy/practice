package com.ybwh.springboot2.sharding;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

public class DatetimeSingleKeyTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Date> {
	private String tablePrefix;
	private DateFormat df;
	
	public DatetimeSingleKeyTableShardingAlgorithm(String tablePrefix, DateFormat df) {
		this.tablePrefix = tablePrefix;
		this.df = df;
	}

	@Override
	public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Date> shardingValue) {

		return tablePrefix + df.format(shardingValue.getValue());
	}

	@Override
	public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Date> shardingValue) {

		Collection<String> result = new LinkedHashSet<>(shardingValue.getValues().size());
		for (Date value : shardingValue.getValues()) {
			result.add(tablePrefix + df.format(value));
		}
		return result;

	}

	@Override
	public Collection<String> doBetweenSharding(Collection<String> availableTargetNames,
			ShardingValue<Date> shardingValue) {
		Collection<String> result = new LinkedHashSet<>();
		Range<Date> ranges = shardingValue.getValueRange();
		Date startTime = ranges.lowerEndpoint();
		Date endTime = ranges.upperEndpoint();
		// 此处应该返回 tablePrefix+201808 , tablePrefix+201809,tablePrefix+201810,
		Calendar cal = Calendar.getInstance();

		while (startTime.getTime() <= endTime.getTime()) {
			result.add(tablePrefix + df.format(startTime));
			cal.setTime(startTime);// 设置起时间
			cal.add(Calendar.MONTH, 1);
			startTime = cal.getTime();
		}
		return result;
	}

}
