package com.shangde.httptest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TestMessagePush {
	
	
	@Test
	public void testPushWithEmployeeId() {
//		String url="http://172.16.140.55:6088/message/push";
//		String url="http://127.0.0.1:6088/message/push";
		String url="http://172.16.100.199:6088/message/push";
		Map<String, String> param = new HashMap<>();
		
		
//		Map<String,Object> extra = new HashMap<>();
//		extra.put("count", 0);
//		extra.put("depType", 4);
//		extra.put("type", 63);
//		extra.put("userName", "万鹏");
		
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("type", 10);
//		dataMap.put("employeeId", 69279);
		dataMap.put("employeeId", 71350);
//		dataMap.put("employeeId", 56331);
//		dataMap.put("employeeId", 61192);
//		dataMap.put("employeeId", 76587);
		dataMap.put("name", "00考勤消息-上班打卡提醒");
		dataMap.put("title", "00考勤消息--上班打卡提醒");
		dataMap.put("content", "上班打卡提醒");
//		dataMap.put("extra", JSON.toJSONString(extra));
		
		
		String data = "["+JSON.toJSONString(dataMap)+"]";
		
//		String data="[{\"content\":\"【报警】报告，系统发现已经超过您上班时间2分钟，但没有打卡记录。快点我打卡吧~\","
//				+ "\"employeeId\":66042,"
//				+ "\"link\":\"\","
//				+ "\"logoUrl\":\"\",\"name\":\"考勤\","
//				+ "\"title\":\"上班迟到报警\",\"type\":12}]";
//		
		
		param.put("data", data);
		param.put("caller", "ehr");
		String result= HttpClientUtil.postForStr(url, param , null);
		System.out.println(result);
		
		
		
	}
	

//	@Test
	public void testPushWithEmployee263() {
		String url="http://172.16.140.55:6088/message/pushWithEmploee263";
//		String url="http://127.0.0.1:6088/message/pushWithEmploee263";
//		String url="http://172.16.100.199:6088/message/pushWithEmploee263";
		Map<String, String> param = new HashMap<>();
		
		String data = "[{"
				+"\"type\":50,"
//				+ "\"employee263\":\"hemeiyi\","
//				+ "\"employee263\":\"yf-sunwei\","
				+ "\"employee263\":\"chenminghui\","
				+ "\"name\":\"00测试-工单##\","
				+ "\"title\":\"00测试-工单###\","
////				+ "\"pushTime\":"+new Date().getTime()+","
////				+ "\"pushTime\":\"2018-05-24\","
				+ "\"content\":\"测试手机是否能收到测试环境推送，收到消息，请通知 杜斐阳 。\","
				+ "}]";
		
//		String data="[{\"content\":\"【报警】报告，系统发现已经超过您上班时间2分钟，但没有打卡记录。快点我打卡吧~\","
//				+ "\"employeeId\":66042,"
//				+ "\"link\":\"\","
//				+ "\"logoUrl\":\"\",\"name\":\"考勤\","
//				+ "\"title\":\"上班迟到报警\",\"type\":12}]";
		
//		String data = "[{"
//				+"\"type\":50,"
//				+ "\"employee263\":\"fanbeibei\","
////				+ "\"employee263\":\"liupenghui\","
//				+ "\"name\":\"00测试-工单\","
//				+ "\"title\":\"00测试-工单\","
////				+ "\"pushTime\":"+new Date().getTime()+","
////				+ "\"pushTime\":\"2018-05-24\","
//				+ "\"content\":\"00测试-工单，线上接口\","
//				+ "}]";
//		
		
		param.put("data", data);
		param.put("caller", "workorder");
		String result= HttpClientUtil.postForStr(url, param , null);
		System.out.println(result);
		
		
		
	}
	
	

	
	@Test
	public void testPushMessage() {
//		String url="http://172.16.140.55:6088/message/pushMessage";
//		String url="http://172.16.140.75:9020/message/pushMessage";
//		String url="http://172.16.101.130:6088/message/pushMessage";
//		String url="http://127.0.0.1:6088/message/pushMessage";
		String url="http://172.16.100.199:6088/message/pushMessage";
		Map<String, String> param = new HashMap<>();
		long time= System.currentTimeMillis();
//		String data = "["
//				+"{"
//				+ "\"account\":\"71350\","
//				+ "\"accountType\":1,"
//				+ "\"name\":\"dss3233242\","
//				+ "\"title\":\"rrrrrr\","
//				+ "\"content\":\"111111111111\","
//				+ "},"
//				+"{"
//				+ "\"account\":\"15872429817\","
//				+ "\"accountType\":3,"
//				+ "\"name\":\"dss3233242\","
//				+ "\"title\":\"rrrrrr\","
//				+ "\"content\":\"222222222222\","
//				+ "},"
//				+"{"
//				+ "\"account\":\"fanbeibei\","
//				+ "\"accountType\":2,"
//				+ "\"name\":\"dss3233242\","
//				+ "\"title\":\"rrrrrr\","
//				+ "\"content\":\"33333333333\","
//				+ "}"
//				+"]";
		
//		String data = "["
//			+"{"
//			+ "\"accountType\":1,"
//			+ "\"account\":\"66039\","
//			+ "\"name\":\"APP运营后台\","
//			+ "\"title\":\"帖子test"+time+"\","
//			+ "\"content\":\"帖子test\""
//			+ "},"
//			+"{"
//			+ "\"accountType\":2,"
//			+ "\"account\":\"hemeiyi\","
//			+ "\"name\":\"APP运营后台\","
//			+ "\"title\":\"帖子test"+time+"\","
//			+ "\"content\":\"帖子test\""
//			+ "}"
//			+"]";
//		
//		String data = "[{\"account\":\"15871005853\",\"accountType\":3,\"content\":\"[提醒]考勤   今日迟到员工\",\"link\":\"\",\"logoUrl\":\"\",\"name\":\"考勤test  今日迟到员工\",\"title\":\"考勤test 今日迟到员工\"}]";
		
//		String data = "[{\"account\":\"15002710001\",\"accountType\":3,"
//				+ "\"pushTime\":\"2018-07-10 16:00:00\","
//				+ "\"content\":\"[提醒]考勤   今日迟到员工\",\"logoUrl\":\"\",\"name\":\"考勤test  今日迟到员工\",\"title\":\"@@@@考勤test 今日迟到员工\",\"link\":\"http://www.baidu.com\"}]";

		
//		String data = "[{\"account\":\"15002710001\",\"accountType\":3,"
//				+ "\"pushTime\":\"2018-07-10 16:00:00\","
//				+ "\"content\":\"[提醒]提成测试\",\"logoUrl\":\"\",\"name\":\"提成测试 test\",\"title\":\"@@@@提成测试 test\"}]";

		
		
//		String data = "[{\"account\":\"13517287360\",\"accountType\":3,"
//				+ "\"pushTime\":\"2018-07-10 16:00:00\","
//				+ "\"content\":\"[提醒]考勤   今日迟到员工\",\"link\":\"\",\"logoUrl\":\"\",\"name\":\"考勤test  今日迟到员工\",\"title\":\"@@@@考勤test 今日迟到员工\"}]";


		String data ="[{\"account\":\"71350\","
				+ "\"accountType\":1,"
				+ "\"content\":\"会议室test备注test\","
				+ "\"name\":\"会议室\","
				+ "\"title\":\"会议室预定test\"}]";
//		String data ="[{\"account\":\"61192\",\"accountType\":1,\"link\":\"https://www.baidu.com/\",\"content\":\"陈威,您好!您已成功预定会议室2A波尔图 会议主题test备注test\",\"name\":\"会议室预定\",\"title\":\"会议室预定2A波尔图会议室预定成功\"}]";
		
	
		

//		String data ="[{\"account\":\"112275\",\"accountType\":1,\"content\":\"会议室test备注test,time="+time+"\",\"name\":\"会议室预定\",\"title\":\"会议室预定2A波尔图会议室预定成功\"}]";
		param.put("data", data);
//		param.put("systemName", "workorder");
		param.put("systemName", "meetingroom");
//		param.put("systemName", "message_platform_warn");
//		param.put("systemName", "article_state");
//		param.put("systemName", "elearn");
		
//		param.put("systemName", "percentage");
//		param.put("systemName", "attendance_later_employee");
		
		
		String result= HttpClientUtil.postForStr(url, param , null);
		System.out.println(result);
		
		
		
	}
	
	
	@Test
	public void testPushMessageTimer() throws IOException {
		String url="http://172.16.140.55:6088/message/pushMessage";
//		String url="http://127.0.0.1:6088/message/pushMessage";
//		String url="http://172.16.100.199:6088/message/pushMessage";
		Map<String, String> param = new HashMap<>();
		
//		String data = "["
//				+"{"
//				+ "\"account\":\"71350\","
//				+ "\"accountType\":1,"
//				+ "\"name\":\"dss3233242\","
//				+ "\"title\":\"rrrrrr\","
//				+ "\"content\":\"111111111111\","
//				+ "},"
//				+"{"
//				+ "\"account\":\"15872429817\","
//				+ "\"accountType\":3,"
//				+ "\"name\":\"dss3233242\","
//				+ "\"title\":\"rrrrrr\","
//				+ "\"content\":\"222222222222\","
//				+ "},"
//				+"{"
//				+ "\"account\":\"fanbeibei\","
//				+ "\"accountType\":2,"
//				+ "\"name\":\"dss3233242\","
//				+ "\"title\":\"rrrrrr\","
//				+ "\"content\":\"33333333333\","
//				+ "}"
//				+"]";
		
		String data = "["
			+"{"
			+ "\"accountType\":1,"
			+ "\"account\":\"112275\","
			+ "\"name\":\"APP运营后台\","
			+ "\"title\":\"你的学员有一条帖子被屏蔽\","
			+ "\"pushTime\":\"2018-06-26 14:22:22\","
			+ "\"content\":\"帖子id616311、帖子地址 http://172.16.140.69:8003/community-pc-war/#post/616311、一级版块名称自变量学院、 发帖标题老哥带你给飞、发帖人昵称同二级多个不同、发帖人手机号15800000004、发帖时间2018-06-26 14:20:17、发帖内容妈逼真他妈烦\","
			+ "\"link\":\"http://172.16.140.69:8003/community-pc-war/#post/616315\""
			+ "}"
			+"]";
		
		

		

//		String data ="[{\"account\":\"weijinlong13\",\"accountType\":2,\"content\":\"陈威,您好!您已成功预定会议室2A波尔图 会议主题test备注test\",\"name\":\"会议室预定\",\"title\":\"会议室预定2A波尔图会议室预定成功\"}]";
//		String data ="[{\"account\":\"heguifang\",\"accountType\":1,\"content\":\"陈威,您好!您已成功预定会议室2A波尔图 会议主题test备注test\",\"name\":\"会议室预定\",\"title\":\"会议室预定2A波尔图会议室预定成功\"}]";
		
//		String data ="[{\"account\":\"fanbeibei\",\"accountType\":2,\"content\":\"陈威,您好!您已成功预定会议室2A波尔图 会议主题test备注test\",\"name\":\"会议室预定\",\"title\":\"会议室预定2A波尔图会议室预定成功\"}]";
		param.put("data", data);
//		param.put("systemName", "workorder");
		param.put("systemName", "meetingroom");
//		param.put("systemName", "message_platform_warn");
		param.put("systemName", "article_state");
		
		ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
		
		executorService.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				String result= HttpClientUtil.postForStr(url, param , null);
				System.out.println(result);
				
			}
		}, 5, 30, TimeUnit.SECONDS);
		
		
		System.in.read();
		
		
	}

}
