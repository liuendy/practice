package com.ybwh.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;

public class Producer {

	private DefaultMQProducer defaultMQProducer;
	private TransactionMQProducer transactionMQProducer;
	

	public Producer(String producerGroup, String namesrvAddr, String instanceName, boolean transation) {

	}

}
