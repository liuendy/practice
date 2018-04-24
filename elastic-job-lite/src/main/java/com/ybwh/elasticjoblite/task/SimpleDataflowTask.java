package com.ybwh.elasticjoblite.task;

import java.util.ArrayList;
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

	// 抓取数据逻辑
	public List<User> fetchData(ShardingContext shardingContext) {
		LOGGER.info("抓取数据开始");
		List<User> list = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			list.add(new User((long)i,"u"+i,"p"+i));
		}
		LOGGER.info("抓取数据结束");

		return list;
	}

	// 处理数据逻辑,data是fetchData抓取得数据
	public void processData(ShardingContext shardingContext, List<User> data) {
		LOGGER.info("处理数据");
		for (User user : data) {
			System.out.println(user);
			
		}
		

	}
}
