package com.ybwh.tranformData.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "JONI_TABLE")
public class RegIdPo {

	@Column(name = "reg_id")
	private String regId;
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "device_type")
	private String deviceType;
	/**
	 * 应用版本号
	 */
	@Column(name = "app_version")
	private String appVersion;
	@Column(name = "device_model")
	private String deviceModel;

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "{regId=" + regId + ", employeeId=" + employeeId + "}";
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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
