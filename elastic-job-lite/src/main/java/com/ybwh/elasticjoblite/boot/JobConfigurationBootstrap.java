package com.ybwh.elasticjoblite.boot;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.ybwh.elasticjoblite.task.StandaloneSimpleTask;

/**
 * 编程式配置启动器(不推荐)
 */
public class JobConfigurationBootstrap {

	private static CoordinatorRegistryCenter createRegistryCenter() {
		CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(
				new ZookeeperConfiguration("127.0.0.1:2181", "test-job"));
		regCenter.init();
		return regCenter;
	}

	private static LiteJobConfiguration createJobConfiguration() {
		// 创建作业配置
		// 定义作业核心配置
		JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration
				.newBuilder("standaloneSimpleTask", "0/15 * * * * ?", 1).build();
		// 定义SIMPLE类型配置
		SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig,
				StandaloneSimpleTask.class.getCanonicalName());
		// 定义Lite作业根配置
		LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();
		return simpleJobRootConfig;
	}

	public static void main(String[] args) {
		new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
	}
}
