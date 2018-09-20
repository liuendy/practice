package com.ybwh.springboot1.sharding.report;

import com.ybwh.springboot1.sharding.FixedDBShardingAlgorithm;

/**
 * t_report的分库策略
 *
 */
public class ReportDBRangeShardingAlgorithm extends FixedDBShardingAlgorithm<Comparable<?>> {

	public ReportDBRangeShardingAlgorithm() {
		super("ds0");
	}
}
