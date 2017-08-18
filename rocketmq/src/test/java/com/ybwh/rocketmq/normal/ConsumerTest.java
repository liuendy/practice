package com.ybwh.rocketmq.normal;

import java.io.IOException;
import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

public class ConsumerTest {
	public static void main(String[] args) throws MQClientException, IOException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("pushConsumerGroup");

		consumer.setNamesrvAddr("s2:9876;s3:9876;s4:9876");
//		consumer.setInstanceName("Producer");
		consumer.subscribe("SELF_TEST_TOPIC", "*");

		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs);
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		
		consumer.start();
		
//		System.in.read();

	}
}
