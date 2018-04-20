package com.ybwh.quartz.simple;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("***************" + context.getTrigger().getJobKey().getName() + "/"
				+ context.getTrigger().getJobKey().getName() + "," + context.getJobDetail().getKey());
	}

}
