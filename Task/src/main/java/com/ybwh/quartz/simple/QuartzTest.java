package com.ybwh.quartz.simple;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

	public static void main(String[] args) {

		try {
			// Grab the Scheduler instance from the Factory
			/**
			 * 任务调度器，是实际执行任务调度的控制器。在spring中通过SchedulerFactoryBean封装起来。
			 */
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			
			/**
			 * 触发器，用于定义任务调度的时间规则，有SimpleTrigger,CronTrigger,DateIntervalTrigger
			 * 和NthIncludedDayTrigger，其中CronTrigger用的比较多，本文主要介绍这种方式。
			 * CronTrigger在spring中封装在CronTriggerFactoryBean中。
			 */

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1") // 定义name/group
					.startNow()// 一旦加入scheduler，立即生效
					.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")) // 使用cronTrigger
					.build();
			
			Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2") // 定义name/group
					.startNow()// 一旦加入scheduler，立即生效
					.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")) // 使用cronTrigger
					.build();
			
			
			/**
			 * 是一个接口，只有一个方法void execute(JobExecutionContext
			 * context),开发者实现该接口定义运行任务，JobExecutionContext类
			 */
			Job job;

			/**
			 * 用来描述Job实现类及其它相关的静态信息，如Job名字、关联监听器等信息。
			 */
			JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("myJob").build();
			JobDetail jobDetail2 = JobBuilder.newJob(MyJob.class).withIdentity("myJob2").build();
			

			scheduler.scheduleJob(jobDetail, trigger);
			

			// and start it off
			scheduler.start();
			
			scheduler.scheduleJob(jobDetail2, trigger2);
			scheduler.triggerJob(jobDetail2.getKey());
			scheduler.deleteJob(jobDetail2.getKey());
			
			scheduler.pauseJob(JobKey.jobKey("myJob2", "group2"));//暂停
			scheduler.resumeJob(JobKey.jobKey("myJob2", "group2"));//恢复

//			scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
}