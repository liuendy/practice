package com.ybwh.hbase.endpoint;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.Region;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.zookeeper.Op.Create;

public class SplitRegionObserver extends BaseRegionObserver {

	@Override
	public void prePut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability)
			throws IOException {

		

		// 获取region的startKey,用来作为前缀构建二级索引的rowkey，确保二级索引和对应的数据会保存在同一个region上
		HRegionInfo regionInfo = e.getEnvironment().getRegionInfo();
		byte[] regionStartKey = regionInfo.getStartKey();

		//
		byte[] dataRowKey = put.getRow();
		byte[] indexRowKey = createIndexRowKey(regionStartKey);
		Cell cell = put.get("indexFamily".getBytes(), "indexQualifier".getBytes()).get(0);
		Put putIndex = new Put(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
		putIndex.addColumn("indexFamily".getBytes(), "indexQualifier".getBytes(), row);

	}

	private byte[] createIndexRowKey(byte[] regionStartKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preSplit(ObserverContext<RegionCoprocessorEnvironment> c, byte[] splitRow) throws IOException {

	}

	@Override
	public void postSplit(ObserverContext<RegionCoprocessorEnvironment> e, Region l, Region r) throws IOException {
	}

	@Override
	public void preSplitBeforePONR(ObserverContext<RegionCoprocessorEnvironment> ctx, byte[] splitKey,
			List<Mutation> metaEntries) throws IOException {
	}

	@Override
	public void preSplitAfterPONR(ObserverContext<RegionCoprocessorEnvironment> ctx) throws IOException {
	}

}
