package com.ybwh.quartz.cron;

import java.util.Date;

import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * 测试cron表达式
 * @author fan79
 *
 */
public class CronSequenceGeneratorTest {  
  
    public static void main(String[] args) {  
  
        String cron = "* */5 * * * ?"; //每个五分钟执行一次  
  
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);  
  
        Date currentTime = new Date();
  
        System.out.println("currentTime: " + currentTime);  
  
        Date nextTimePoint = cronSequenceGenerator.next(currentTime); // currentTime为计算下次时间点的开始时间  
        System.out.println("nextTimePoint:     " + nextTimePoint.getTime());  
  
        Date nextNextTimePoint = cronSequenceGenerator.next(nextTimePoint);  
  
        System.out.println("nextNextTimePoint: " + nextNextTimePoint.getTime());  
    }  
} 