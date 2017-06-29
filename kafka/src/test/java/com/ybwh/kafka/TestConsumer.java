package com.ybwh.kafka;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

public class TestConsumer {
//	@Test
	public void testPoll() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");// 该地址是集群的子集，用来探测集群。
		props.put("group.id", "test");// cousumer的分组id
		props.put("enable.auto.commit", "false");// 自动提交offsets
		// 设置使用最开始的offset偏移量为该group.id的最早。如果不设置，则会是latest即该topic最新一个消息的offset
		// 如果采用latest，消费者只能得道其启动后，生产者生产的消息
		props.put("auto.offset.reset", "earliest");

		props.put("session.timeout.ms", "30000");// Consumer向集群发送自己的心跳，超时则认为Consumer已经死了，kafka会把它的分区分配给其他进程
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");// 反序列化器
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("max.poll.records", "50");

		KafkaConsumer<String, String> consumer = null;

		try {
			consumer = new KafkaConsumer<>(props);
			consumer.subscribe(Arrays.asList("my-topic"));// 订阅的topic,可以多个
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
				for (TopicPartition partition : records.partitions()) {
					List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
					for (ConsumerRecord<String, String> record : partitionRecords) {
						System.out.println("now consumer the message it's offset is :" + record.offset()
								+ " and the value is :" + record.value());
					}
					long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
					System.out.println("now commit the partition[ " + partition.partition() + "] offset");
					consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != consumer) {
				consumer.close();
			}
		}
	}
}
