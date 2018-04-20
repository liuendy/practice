package com.ybwh.quartz.cron;

import java.util.Date;

import com.ybwh.quartz.cron.parser.CronSequenceGenerator;


/**
 * ����cron���ʽ
 * @author fan79
 *
 */
public class CronSequenceGeneratorTest {  
  
    public static void main(String[] args) {  
  
        String cron = "* */5 * * * ?"; //ÿ�������ִ��һ��  
  
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);  
  
        Date currentTime = new Date();
  
        System.out.println("currentTime: " + currentTime);  
  
        Date nextTimePoint = cronSequenceGenerator.next(currentTime); // currentTimeΪ�����´�ʱ���Ŀ�ʼʱ��  
        System.out.println("nextTimePoint:     " + nextTimePoint.getTime());  
  
        Date nextNextTimePoint = cronSequenceGenerator.next(nextTimePoint);  
  
        System.out.println("nextNextTimePoint: " + nextNextTimePoint.getTime());  
    }  
} 