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
 * XML的配置方式则是streaming-process属性，boolean类型。当作业配置为流式的时候，
 * 每次触发作业后会调度一次fetchData获取数据，如果获取到了数据会调度processData方法处理数据，
 * 处理完后又继续调fetchData获取数据，再调processData处理，如此循环，就像流水一样。
 * 直到fetchData没有获取到数据或者发生了重新分片才会停止。
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
