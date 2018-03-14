package com.ybwh.quartz.cron;

import java.text.ParseException;
import java.util.Date;

import com.ybwh.quartz.cron.parser.CronExpression;


/**
 * ≤‚ ‘cron±Ì¥Ô Ω
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
