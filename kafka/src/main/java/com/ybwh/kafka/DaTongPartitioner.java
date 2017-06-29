package com.ybwh.kafka;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaTongPartitioner implements Partitioner {
	private static Logger s_logger = LoggerFactory.getLogger(DaTongPartitioner.class);

	@Override
	public void configure(Map<String, ?> configs) {

	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
		int numPartitions = partitions.size();
		int partitionNum = 0;
		try {
			partitionNum = Integer.parseInt((String) key);
		} catch (Exception e) {
			partitionNum = key.hashCode();
		}
		s_logger.debug("the message sendTo topic:" + topic + " and the partitionNum:" + partitionNum);
		return Math.abs(partitionNum % numPartitions);
	}

	@Override
	public void close() {

	}

}