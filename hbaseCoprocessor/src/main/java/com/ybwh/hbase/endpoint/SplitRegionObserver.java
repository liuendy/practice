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

public class SplitRegionObserver extends BaseRegionObserver {
	
	private static final String INDEX_FAMILY = "indexFamily";
	private static final String INDEX_QUALIFIER = "indexQualifier";

	@Override
	public void prePut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability)
			throws IOException {

		// 获取region的startKey,用来作为前缀构建二级索引的rowkey，确保二级索引和对应的数据会保存在同一个region上
		HRegionInfo regionInfo = e.getEnvironment().getRegionInfo();
		byte[] regionStartKey = regionInfo.getStartKey();

		byte[] dataRowKey = put.getRow();
		byte[] indexRowKey = createIndexRowKey(regionStartKey, dataRowKey);
		
		//保存索引数据
		Cell cell = put.get(INDEX_FAMILY.getBytes(), INDEX_QUALIFIER.getBytes()).get(0);
		Put putIndex = new Put(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
		putIndex.addColumn(INDEX_FAMILY.getBytes(), INDEX_QUALIFIER.getBytes(), indexRowKey);
		
		

	}

	/**
	 * 根据region的startKey和数据的rowKey创建索引的rowKey
	 * 
	 * @param regionStartKey
	 * @param dataRowKey
	 * @return
	 */
	private byte[] createIndexRowKey(byte[] regionStartKey, byte[] dataRowKey) {
		// indexRowKey = regionStartKey + | + dataRowKey

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
