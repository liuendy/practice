package com.ybwh.springboot2.sharding.report;

import com.ybwh.springboot2.sharding.FixedDBShardingAlgorithm;

/**
 * t_report的分库策略
 *
 */
public class ReportDBRangeShardingAlgorithm extends FixedDBShardingAlgorithm<Comparable<?>> {

	public ReportDBRangeShardingAlgorithm() {
		super("ds0");
	}
}
