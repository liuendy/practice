package com.ybwh.tranformData.transform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ybwh.tranformData.jdbc.EntityMapper;
import com.ybwh.tranformData.po.PushTokenInfo;
import com.ybwh.tranformData.po.RegIdPo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

@Component
public class Transformer {
	private static final Logger logger = LoggerFactory.getLogger(Transformer.class);
	public static final String MESSAGE_TOKEN_KEY_PREFIX="message:token:";
	@Autowired
	private JedisPool pool;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void startTransformMysqlToRedis() {
//		System.out.println("del  old key ....");
//		delKey(MESSAGE_TOKEN_KEY_PREFIX);
//		System.out.println("del  old key success!!");
		// System.out.println(jdbcTemplate);

		List<RegIdPo> list = getRegId();
		// logger.debug("list = {}",list.toString());
		
		
		

		if (null != list && list.size() > 0) {
			System.out.println( "**** total  get "+ list.size()+"  regId info from mysql!!");
			
			Jedis jedis = null;
			int count =0;
			int nullCount = 0;

			try {

				
				jedis = pool.getResource();
				Pipeline pipeLine = jedis.pipelined();
				for (RegIdPo p : list) {

					if (null == p.getRegId() || "".equals(p.getRegId().trim())) {
						nullCount++;
						continue;
					}
					
					count++;

					PushTokenInfo token = new PushTokenInfo();
					if ("android".equalsIgnoreCase(p.getDeviceType())) {
						token.setOs("android");
					}

					if ("IOS".equalsIgnoreCase(p.getDeviceType())) {
						token.setOs("ios");
					}

					token.setPushEvidence(p.getRegId());
					token.setEmployeeId(p.getEmployeeId());
					token.setAppVersion(p.getAppVersion());
					token.setType(1);
					token.setDeviceModel(p.getDeviceModel());
					
					pipeLine.set(MESSAGE_TOKEN_KEY_PREFIX+p.getEmployeeId(), JSON.toJSONString(token));
					
					
					if(count / 100 == 0) {
						pipeLine.sync();
					}
				}
				
				pipeLine.sync();

			} finally {
				if (null != jedis) {
					jedis.close();
				}
			}
			
			System.out.println("transformer complete!,total "+count+" to redis, "+nullCount +" has null regId !!");

		}else {
			System.out.println("*** get no   regId info  from mysql");
		}

		
	}

	private List<RegIdPo> getRegId() {
//		String sql =  "SELECT t1.reg_id,t1.employee_id,t1.device_type "
//				+ "  FROM t_work_attendance_remind_info t1,`t_employee_base_info`  t2 ";

		String sql =  "SELECT t1.reg_id,t1.employee_id,t1.device_type,t1.app_version,t1.device_model "
				+ "  FROM t_employeeid_regid t1,`t_employee_base_info`  t2 "
				+ "  WHERE t1.employee_id=t2.employee_id AND t2.status=0 AND  t2.work_state !=5 ";

		logger.info(sql);
		System.out.println(sql);
		List<RegIdPo> list = jdbcTemplate.query(sql, new Object[] {}, new EntityMapper(RegIdPo.class));

		return list;

	}
	
	
	public void delKey(String  prefix) {
		Jedis jedis = null;
		HashSet<String> set = new HashSet<String>();
		
		try {
			jedis = pool.getResource();
			Pipeline pipeLine = jedis.pipelined();

			String cursor = "0";
			while (true) {
				ScanParams params = new ScanParams().match(prefix + "*").count(10);
				ScanResult<String> result = jedis.scan(cursor, params);
				List<String> list = result.getResult();
				if (null != list && list.size() > 0) {
					for (String s : list) {
						if(!set.contains(s)) {
							pipeLine.del(s);
							System.out.println("delete "+s);
							set.add(s);
						}
						
					}
					System.out.println("del  "+list.size()+"  !!");
				}
				
				pipeLine.sync();

				
				
				cursor = result.getStringCursor();
				if ("0".equals(cursor)) {
					break;
				}

			}
			System.out.println("total del  "+set.size()+"  !!");

		} catch (Throwable cause) {
			cause.printStackTrace();
			logger.error("redis scan error,{}", ExceptionUtils.getStackTrace(cause));
		} finally {
			if (null != jedis) {
				jedis.close();
			}
		}

	}

}
