package com.ybwh.quartz.cron;

import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

public class ScheduledAnnotationBeanPostProcessorTest {

	public static void main(String[] args) {
		/**
		 * ScheduledAnnotationBeanPostProcessor��processScheduled�����к���ִ�ж�ʱ������߼�
		 */
		ScheduledAnnotationBeanPostProcessor processor = null;

		/**
		 * cron���ʽ����ִ������ReschedulingRunnable��schedule��������CronTrigger��nextExecutionTime��
		 * CronTrigger��nextExecutionTime�ֵ���CronSequenceGenerator��next������ȡ�´�ִ��ʱ�䣬
		 * Ȼ�󽻸�ScheduledExecutorService��scheduleִ�С�
		 * ���˽���ScheduledExecutorService(ʵ����������ScheduledThreadPoolExecutor)���ӳ�ִ��һ����������߼���
		 * ReschedulingRunnable rr =null;
		 * 
		 */

	}

}
