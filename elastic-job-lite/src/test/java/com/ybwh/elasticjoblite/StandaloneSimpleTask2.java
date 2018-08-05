package com.ybwh.elasticjoblite;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ybwh.elasticjoblite.task.StandaloneSimpleTask;

public class StandaloneSimpleTask2 extends StandaloneSimpleTask{

	public static void main(String[] args) {
		System.out.println(StandaloneSimpleTask.class.isAssignableFrom(StandaloneSimpleTask2.class));
		System.out.println(SimpleJob.class.isAssignableFrom(StandaloneSimpleTask2.class));
	}

}
