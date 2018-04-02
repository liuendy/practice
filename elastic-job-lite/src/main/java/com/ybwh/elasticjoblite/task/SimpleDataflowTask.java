package com.ybwh.elasticjoblite.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;

/**
 * 简单的数据流任务
 *
 */
public class SimpleDataflowTask implements DataflowJob {


    public List fetchData(ShardingContext shardingContext) {


        return null;
    }

    public void processData(ShardingContext shardingContext, List data) {



    }
}
