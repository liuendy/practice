package com.ybwh.hbase.endpoint;

import java.io.IOException;

import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;

public class SplitRegionObserver extends BaseRegionObserver {

	@Override
	public void preSplit(ObserverContext<RegionCoprocessorEnvironment> c, 
			byte[] splitRow) throws IOException {
		
	}

}
