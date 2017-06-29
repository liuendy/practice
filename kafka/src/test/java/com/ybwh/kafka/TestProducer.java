package com.ybwh.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class TestProducer {
//	@Test
	public void test() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");// ByteArraySerializer
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("partitioner.class", "com.ybwh.kafka.DaTongPartitioner");

		Producer<String, String> producer = new KafkaProducer<>(props);

		try {

			for (int i = 0; i < 100; i++) {
				producer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i), Integer.toString(i)),
						new Callback() {
							@Override
							public void onCompletion(RecordMetadata metadata, Exception exception) {
								System.out.println("callback  " + metadata);
							}
						});
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}
}
