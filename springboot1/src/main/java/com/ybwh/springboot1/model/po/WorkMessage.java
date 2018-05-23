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
@Table(name = "t_work_message", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class WorkMessage {

	/**
	*主键
	*/
	@Id
	@SequenceGenerator(name = "t_work_message")
	@Column(name = "id")
	private Long id;
	
	/**
	*消息类型。10-考勤之上班打卡提醒；11-考勤之下班打卡提醒；12-考勤之上班迟到报警；13-考勤之今日迟到员工；14-考勤之昨日缺卡；15-考勤之本月异常考勤；16-考勤之排班提醒；20-审批之审批提醒；30-提成之提成更新提醒；40-会议室之会议室预定成功
	*/
	@Column(name = "type")
	private Integer type;
	
	/**
	*消息名称(应用名称)
	*/
	@Column(name = "name")
	private String name;
	
	/**
	*logo地址
	*/
	@Column(name = "logo_url")
	private String logoUrl;
	
	/**
	*消息标题
	*/
	@Column(name = "title")
	private String title;
	
	/**
	*消息内容
	*/
	@Column(name = "content")
	private String content;
	
	/**
	*消息状态。0-未推送成功，1-已推送
	*/
	@Column(name = "state")
	private Integer state;
	
	/**
	*消息状态。0-未读，1-已读
	*/
	@Column(name = "is_read")
	private Integer isRead;
	
	/**
	*消息链接
	*/
	@Column(name = "link")
	private String link;
	
	/**
	*接收消息的员工ID
	*/
	@Column(name = "employee_id")
	private Long employeeId;
	
	/**
	*推送时间
	*/
	@Column(name = "push_time")
	private Date pushTime;
	
	/**
	*数据创建时间
	*/
	@Column(name = "create_ts")
	private Date createTs;
	
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
	* 设置 type 
	* @param type 消息类型。10-考勤之上班打卡提醒；11-考勤之下班打卡提醒；12-考勤之上班迟到报警；13-考勤之今日迟到员工；14-考勤之昨日缺卡；15-考勤之本月异常考勤；16-考勤之排班提醒；20-审批之审批提醒；30-提成之提成更新提醒；40-会议室之会议室预定成功 
	*/ 
	public void setType(Integer type) {
		this.type = type;
	}

	/** 
	* 获取 消息类型。10-考勤之上班打卡提醒；11-考勤之下班打卡提醒；12-考勤之上班迟到报警；13-考勤之今日迟到员工；14-考勤之昨日缺卡；15-考勤之本月异常考勤；16-考勤之排班提醒；20-审批之审批提醒；30-提成之提成更新提醒；40-会议室之会议室预定成功 
	* @return type 
	*/
	public Integer getType() {
		return type;
	}

	/** 
	* 设置 name 
	* @param name 消息名称(应用名称) 
	*/ 
	public void setName(String name) {
		this.name = name;
	}

	/** 
	* 获取 消息名称(应用名称) 
	* @return name 
	*/
	public String getName() {
		return name;
	}

	/** 
	* 设置 logoUrl 
	* @param logoUrl logo地址 
	*/ 
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	/** 
	* 获取 logo地址 
	* @return logoUrl 
	*/
	public String getLogoUrl() {
		return logoUrl;
	}

	/** 
	* 设置 title 
	* @param title 消息标题 
	*/ 
	public void setTitle(String title) {
		this.title = title;
	}

	/** 
	* 获取 消息标题 
	* @return title 
	*/
	public String getTitle() {
		return title;
	}

	/** 
	* 设置 content 
	* @param content 消息内容 
	*/ 
	public void setContent(String content) {
		this.content = content;
	}

	/** 
	* 获取 消息内容 
	* @return content 
	*/
	public String getContent() {
		return content;
	}

	/** 
	* 设置 state 
	* @param state 消息状态。0-未推送成功，1-已推送 
	*/ 
	public void setState(Integer state) {
		this.state = state;
	}

	/** 
	* 获取 消息状态。0-未推送成功，1-已推送 
	* @return state 
	*/
	public Integer getState() {
		return state;
	}

	/** 
	* 设置 isRead 
	* @param isRead 消息状态。0-未读，1-已读 
	*/ 
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	/** 
	* 获取 消息状态。0-未读，1-已读 
	* @return isRead 
	*/
	public Integer getIsRead() {
		return isRead;
	}

	/** 
	* 设置 link 
	* @param link 消息链接 
	*/ 
	public void setLink(String link) {
		this.link = link;
	}

	/** 
	* 获取 消息链接 
	* @return link 
	*/
	public String getLink() {
		return link;
	}

	/** 
	* 设置 employeeId 
	* @param employeeId 接收消息的员工ID 
	*/ 
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	/** 
	* 获取 接收消息的员工ID 
	* @return employeeId 
	*/
	public Long getEmployeeId() {
		return employeeId;
	}

	/** 
	* 设置 pushTime 
	* @param pushTime 推送时间 
	*/ 
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	/** 
	* 获取 推送时间 
	* @return pushTime 
	*/
	public Date getPushTime() {
		return pushTime;
	}

	/** 
	* 设置 createTs 
	* @param createTs 数据创建时间 
	*/ 
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	/** 
	* 获取 数据创建时间 
	* @return createTs 
	*/
	public Date getCreateTs() {
		return createTs;
	}

	
}