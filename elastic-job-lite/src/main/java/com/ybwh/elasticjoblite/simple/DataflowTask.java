package com.ybwh.elasticjoblite.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;

public class DataflowTask implements DataflowJob {


    public List fetchData(ShardingContext shardingContext) {


        return null;
    }

    public void processData(ShardingContext shardingContext, List data) {



    }
}
