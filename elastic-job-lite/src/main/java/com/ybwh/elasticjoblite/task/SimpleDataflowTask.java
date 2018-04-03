package com.ybwh.elasticjoblite.task;

import java.util.List;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.ybwh.elasticjoblite.dto.User;

/**
 * 简单的数据流任务
 * 每次调度触发的时候都会先调fetchData获取数据，如果获取到了数据再调度processData方法处理数据。
 * DataflowJob在运行时有两种方式，流式的和非流式的，通过属性streamingProcess控制，如果是基于Spring
 * XML的配置方式则是streaming-process属性，boolean类型。流式处理数据只有fetchData方法的返回值为null或集合长度为空时，
 * 作业才停止抓取，否则作业将一直运行下去； 非流式处理数据则只会在每次作业执行过程中执行一次fetchData方法和processData方法，随即完成本次作业。
 *
 */
public class SimpleDataflowTask implements DataflowJob<User> {
	
	private static final Logger LOGGER = Logger.getLogger(SimpleDataflowTask.class);

	public List<User> fetchData(ShardingContext shardingContext) {// 抓取数据逻辑
		switch (shardingContext.getShardingItem()) {
		case 0:
			List<User> data = null;// get data from database by sharding item 0
			return data;
		case 1:
			List<User> data1 = null;// get data from database by sharding item 1
			return data1;
		case 2:
			List<User> data2 = null;// get data from database by sharding item 2
			return data2;
		// case n: ...
		}

		return null;
	}

	public void processData(ShardingContext shardingContext, List<User> data) {// 处理数据逻辑,data是fetchData抓取得数据

	}
}
