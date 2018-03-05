package com.ybwh.elasticjoblite.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class TestTask implements SimpleJob{

	@Override
	public void execute(ShardingContext context) {
		System.out.println("dddddd");
		
	}

}
