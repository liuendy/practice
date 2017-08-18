package com.ybwh.rocketmq.normal;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class NormalProducer {
	private String producerGroup;
	private String namesrvAddr;
	private String instanceName;

	private DefaultMQProducer producer;

	public NormalProducer(String producerGroup, String namesrvAddr, String instanceName) throws MQClientException {
		this.producerGroup = producerGroup;
		this.namesrvAddr = namesrvAddr;
		this.instanceName = instanceName;

		init();
	}

	public void init() throws MQClientException {
		final DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
		producer.setNamesrvAddr("s2:9876;s3:9876;s4:9876");
		producer.setInstanceName("Producer");
		/**
		 * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		 * 注意：切记不可以在每次发送消息时，都调用start方法
		 */
		producer.start();
	}
	
	
	public  SendResult send(Message msg) throws MQClientException, RemotingException, MQBrokerException, InterruptedException{
		return producer.send(msg);
	}
	
	
	public void close(){
		producer.shutdown();
	}

}
