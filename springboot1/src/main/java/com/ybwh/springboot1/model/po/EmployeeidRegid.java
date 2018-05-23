package com.ybwh.springboot1.model.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
*
*/
@Entity 
@Table(name = "t_employeeid_regid", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class EmployeeidRegid {

	/**
	*主键
	*/
	@Id
	@SequenceGenerator(name = "t_employeeid_regid")
	@Column(name = "id")
	private Long id;
	
	/**
	*员工ID
	*/
	@Column(name = "employee_id")
	private Long employeeId;
	
	/**
	*小米regId
	*/
	@Column(name = "reg_id")
	private String regId;
	
	/**
	*设备类型(IOS/Android)
	*/
	@Column(name = "device_type")
	private String deviceType;
	
	/**
	*操作系统版本
	*/
	@Column(name = "os_version")
	private String osVersion;
	
	/**
	*app版本
	*/
	@Column(name = "app_version")
	private String appVersion;
	
	/**
	*设备型号
	*/
	@Column(name = "device_model")
	private String deviceModel;
	
	/**
	*创建时间
	*/
	@Column(name = "create_ts")
	private Date createTs;
	
	/**
	*最后更新时间
	*/
	@Column(name = "update_ts")
	private Date updateTs;
	
	/** 
	* 设置 id 
	* @param id 主键 
	*/ 
	public void setId(Long id) {
		this.id = id;
	}

	/** 
	* 获取 主键 
	* @return id 
	*/
	public Long getId() {
		return id;
	}

	/** 
	* 设置 employeeId 
	* @param employeeId 员工ID 
	*/ 
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	/** 
	* 获取 员工ID 
	* @return employeeId 
	*/
	public Long getEmployeeId() {
		return employeeId;
	}

	/** 
	* 设置 regId 
	* @param regId 小米regId 
	*/ 
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/** 
	* 获取 小米regId 
	* @return regId 
	*/
	public String getRegId() {
		return regId;
	}

	/** 
	* 设置 deviceType 
	* @param deviceType 设备类型(IOS/Android) 
	*/ 
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/** 
	* 获取 设备类型(IOS/Android) 
	* @return deviceType 
	*/
	public String getDeviceType() {
		return deviceType;
	}

	/** 
	* 设置 osVersion 
	* @param osVersion 操作系统版本 
	*/ 
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	/** 
	* 获取 操作系统版本 
	* @return osVersion 
	*/
	public String getOsVersion() {
		return osVersion;
	}

	/** 
	* 设置 appVersion 
	* @param appVersion app版本 
	*/ 
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	/** 
	* 获取 app版本 
	* @return appVersion 
	*/
	public String getAppVersion() {
		return appVersion;
	}

	/** 
	* 设置 deviceModel 
	* @param deviceModel 设备型号 
	*/ 
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	/** 
	* 获取 设备型号 
	* @return deviceModel 
	*/
	public String getDeviceModel() {
		return deviceModel;
	}

	/** 
	* 设置 createTs 
	* @param createTs 创建时间 
	*/ 
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	/** 
	* 获取 创建时间 
	* @return createTs 
	*/
	public Date getCreateTs() {
		return createTs;
	}

	/** 
	* 设置 updateTs 
	* @param updateTs 最后更新时间 
	*/ 
	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

	/** 
	* 获取 最后更新时间 
	* @return updateTs 
	*/
	public Date getUpdateTs() {
		return updateTs;
	}

	
}