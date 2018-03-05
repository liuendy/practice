package com.ybwh.elasticjoblite;
import java.text.ParseException;
import java.util.Date;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class DiaoduTest implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(new Date() + "天佑最帅！！！");
        
    }
    public static void main(String[] args) {
        JobDetail detail = new JobDetail("job1", "group1", DiaoduTest.class);
        CronTrigger cronTrigger = new CronTrigger("job1", "group1");
        try {
            CronExpression cronExpression = new CronExpression("0/1 * * * * ?");
            cronTrigger.setCronExpression(cronExpression);
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler;
            try {
                scheduler = factory.getScheduler();
                try {
                    scheduler.scheduleJob(detail, cronTrigger);
                    scheduler.start();
                } catch (SchedulerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (SchedulerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}