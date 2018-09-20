package com.ybwh.springboot1;

import java.time.LocalDateTime;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class TestLocalDateTime {
	@Test
	public void testFastjson() {
		LocalDateTime now = LocalDateTime.now();
		Data d = new Data();
		d.setCreateTime(now);
		System.out.println(JSON.toJSON(d));
		
		
		Data d2 = JSON.parseObject("{\"createTime\":\"2018-08-16 18:14:49\"}", Data.class);
		System.out.println(d2);
		
	}

}


class Data{
	private LocalDateTime createTime;

	public LocalDateTime getCreateTime() {
		return createTime;
	}
	

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}


	@Override
	public String toString() {
		return "Data [createTime=" + createTime + "]";
	}
	
}