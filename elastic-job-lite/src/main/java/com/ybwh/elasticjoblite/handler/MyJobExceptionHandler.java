package com.ybwh.elasticjoblite.handler;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;

/**
 * 任务抛出异常的处理器
 *
 */
public class MyJobExceptionHandler implements JobExceptionHandler {

	private static final Logger logger 
            = Logger.getLogger(MyJobExceptionHandler.class);
	
	@Override
	public void handleException(String jobName, Throwable cause) {
		//这里实现任务异常后发短信或邮件提醒功能
		logger.error(String.format("******任务[%s]调度异常", jobName), cause);
	}

}