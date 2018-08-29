package com.ybwh.cron;

import java.util.concurrent.ScheduledExecutorService;

import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

public class ScheduledAnnotationBeanPostProcessorTest {

	public static void main(String[] args) {
		/**
		 * ScheduledAnnotationBeanPostProcessor的processScheduled方法中含有执行定时任务的逻辑
		 * 1.获取CRON表达式
		 * 2.创建交CronTask给ScheduledTaskRegistrar
		 * 3.ScheduledTaskRegistrar的scheduleTasks从各个队列取出任务执行
		 * 
		 */
		ScheduledAnnotationBeanPostProcessor processor = null;
		ScheduledTaskRegistrar tr;
		ScheduledExecutorService s;
		CronTrigger ct;

		/**
		 * cron表达式最终执行者是ReschedulingRunnable的schedule方法调用CronTrigger的nextExecutionTime，
		 * CronTrigger的nextExecutionTime又调用CronSequenceGenerator的next方法获取下次执行时间，
		 * 然后交给ScheduledExecutorService的schedule执行。
		 * 至此进入ScheduledExecutorService(实际上是子类ScheduledThreadPoolExecutor)的延迟执行一次性任务的逻辑。
		 * ReschedulingRunnable rr =null;
		 * 
		 */

	}

}
