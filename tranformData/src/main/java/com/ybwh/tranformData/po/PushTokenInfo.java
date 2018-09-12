package com.ybwh.tranformData.po;

/**   
 *   
 * 小米推送和信鸽推送的 token信息  ，存在redis里
 * @author: Fan Beibei
 * @date:   2018年5月11日 上午10:37:09   
 *
 */  
public class PushTokenInfo {
	
	/**
	 * 员工ID
	 */
	private Long employeeId;
	/**
	 * 推送凭信,小米的填入regId，信鸽填入token
	 */
	private String pushEvidence;
	/**
	 * 手机操作系统(android/IOS,不区分大小写)
	 */
	private String os;
	/**
	 * 类型：1-小米/0-信鸽
	 */
	private Integer type;
	
	/**
	 * 应用版本号
	 */
	private String appVersion;
	
	private String deviceModel;
	
	
	public Long getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getPushEvidence() {
		return pushEvidence;
	}
	
	public void setPushEvidence(String pushEvidence) {
		this.pushEvidence = pushEvidence;
	}
	
	public String getOs() {
		return os;
	}
	
	public void setOs(String os) {
		this.os = os;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}

	public String getAppVersion() {
		return appVersion;
	}
	

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDeviceModel() {
		return deviceModel;
	}
	

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	
	
	
	
	
	

}
