package com.shangde;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class TestFastJson {
	@Test
	public void test() {
		JumpAddrVo ja10 = new JumpAddrVo("/ehr/attendance/ClockInActivity",
				"sdjg://router/sdjgcommunityproject/punchcard", "/ehr/attendance/ClockInActivity",
				"sdjg://router/sdjgcommunityproject/punchcard");
		System.out.println("10*******"+JSONObject.toJSON(ja10));
		
		JumpAddrVo ja15 = new JumpAddrVo("/message/WorkNoticeActivity",
				"sdjg://router/sdjgmessageproject/workmessagevc",null,null);
		System.out.println("15*******"+JSONObject.toJSON(ja15));
		
		JumpAddrVo ja13 = new JumpAddrVo("/ehr/attendance/AttendanceMonitorActivity",
				"sdjg://router/sdjgworkattendanceproject/attendancemonitor","/ehr/attendance/AttendanceMonitorActivity",
				"sdjg://router/sdjgworkattendanceproject/attendancemonitor");
		System.out.println("13*******"+JSONObject.toJSON(ja13));
		
		
		JumpAddrVo ja30 = new JumpAddrVo("/usecenter/myPercentageActivity",
				"sdjg://router/sdjguserinfoproject/me/mypercentage","/usecenter/myPercentageActivity",
				"sdjg://router/sdjguserinfoproject/me/mypercentage");
		System.out.println("30*******"+JSONObject.toJSON(ja30));

	}

}
