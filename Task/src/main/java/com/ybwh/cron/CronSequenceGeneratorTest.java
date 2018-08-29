package com.ybwh.cron;

import java.util.Date;

import org.springframework.scheduling.support.CronSequenceGenerator;



/**
 * 测试spring-task的cron表达式解析器
 * @author fan79
 *
 */
public class CronSequenceGeneratorTest {  
  
    public static void main(String[] args) {  
  
        String cron = "* */5 * * * ?";
  
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);  
  
        Date currentTime = new Date();
  
        System.out.println("currentTime: " + currentTime);  
  
        Date nextTimePoint = cronSequenceGenerator.next(currentTime);
        System.out.println("nextTimePoint:     " + nextTimePoint.getTime());  
  
        Date nextNextTimePoint = cronSequenceGenerator.next(nextTimePoint);  
  
        System.out.println("nextNextTimePoint: " + nextNextTimePoint.getTime());  
    }  
} 