package com.ybwh.tranformData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {
	public static final String MESSAGE_TOKEN_KEY_PREFIX="message:token:";
	@Autowired
	private JedisPool jedisPool;
	
	@Test
	public void testWorkMessageDao() {

		delKey();
	}
	
	
	/**
	 * 用redis scan 扫描获取小米信鸽推送token信息的key
	 * 
	 * @date: 2018年5月11日 下午5:51:50
	 *
	 * @return
	 */
	private void delKey() {

		Set<String> keySet = new HashSet<>();
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"73150");
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"66042");
		
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"65995");
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"66001");
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"66038");
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"66039");
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"66040");
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"66042");
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"66047");
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"66049");
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"66053");
		keySet.add(MESSAGE_TOKEN_KEY_PREFIX+"67214");
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Pipeline pipeLine = jedis.pipelined();
			String cursor = "0";
			while (true) {
				ScanParams params = new ScanParams().match(MESSAGE_TOKEN_KEY_PREFIX + "*").count(10);
				ScanResult<String> result = jedis.scan(cursor, params);
				List<String> list = result.getResult();
				System.out.println(list.size());
				if (null != list && list.size() > 0) {
					for (String s : list) {
						if(!keySet.contains(s)) {
							pipeLine.del(s);
						}
					}
					pipeLine.sync();
				}

				cursor = result.getStringCursor();
				if ("0".equals(cursor)) {
					break;
				}

			}

		} catch (Throwable cause) {
			cause.printStackTrace();
		} finally {
			if (null != jedis) {
				jedis.close();
			}
		}

	}
	
	
	
	
	

}
