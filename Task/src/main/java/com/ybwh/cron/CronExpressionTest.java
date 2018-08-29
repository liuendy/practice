package com.ybwh.cron;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronExpression;



/**
 * 测试quartz的cron表达式解析器。
 * 
 * @author fan79
 *
 */
public class CronExpressionTest {
	public static void main(String[] args) {
		Date curTime = new Date();  
		System.out.println(curTime);  
		  
		CronExpression expression;  
		try   
		{  
		    expression = new CronExpression("0 30 15 * * ?");  
		    Date newDate = expression.getNextValidTimeAfter(curTime);  
		    System.out.println(newDate);  
		} catch (ParseException e) {  
		    e.printStackTrace(); 
		} catch (Exception e) {  
		    e.printStackTrace(); 
		}
	}

}
