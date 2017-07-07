package com.ybwh.kafka;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

public class TestProducer {
	/**
	 * 幂等模式
	 */
	@Test
	public void testIdenpotent() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 3);
		props.put("batch.size", 16384);
		props.put("linger.ms", 0);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");// ByteArraySerializer
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("partitioner.class", "com.ybwh.kafka.DaTongPartitioner");

		Producer<String, String> producer = new KafkaProducer<>(props);

		
		try {
			
			for (int i = 0; i < 100; i++) {
				long start = Calendar.getInstance().getTimeInMillis();
				 Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i), "dsjlosdgosdo"+i),
						new Callback() {
							@Override
							public void onCompletion(RecordMetadata metadata, Exception exception) {
								
								System.out.println(metadata+"   "+ exception);
//								System.out.println((Calendar.getInstance().getTimeInMillis() - start)+"ms,callback  " + metadata);
							}
						});
//				future.get();
//				 System.out.println("$$$"+(Calendar.getInstance().getTimeInMillis() - start));
//				 
//				 System.out.println(future.get().offset());
//				 System.out.println(future.isDone());
////				 System.out.println(future.);
//				System.out.println((Calendar.getInstance().getTimeInMillis() - start)+"ms,callback  ");
			}
			
//			Thread.sleep(30000);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}
	
//	@Test
	public void testTransaction(){
		
		 try {
			Properties props = new Properties();
			 props.put("bootstrap.servers", "localhost:9092");
			 props.put("transactional.id", "my-transactional-id");
			 Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());

			 producer.initTransactions();

			 try {
			     producer.beginTransaction();
			     for (int i = 0; i < 100; i++)
			         producer.send(new ProducerRecord<>("my-topic", Integer.toString(i), Integer.toString(i)));
			     producer.commitTransaction();
			 } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
				 
				 e.printStackTrace();
			     // We can't recover from these exceptions, so our only option is to close the producer and exit.
			     producer.close();
			 } catch (KafkaException e) {
				 e.printStackTrace();
			     // For all other exceptions, just abort the transaction and try again.
			     producer.abortTransaction();
			 }
			 producer.close();
		} catch (ProducerFencedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
