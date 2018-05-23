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
@Table(name = "t_timer_work_message_conf", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class TimerWorkMessageConf {

	/**
	*主键
	*/
	@Id
	@SequenceGenerator(name = "t_timer_work_message_conf")
	@Column(name = "id")
	private Long id;
	
	/**
	*消息类型。10-考勤之上班打卡提醒；11-考勤之下班打卡提醒；12-考勤之上班迟到报警；13-考勤之今日迟到员工；14-考勤之昨日缺卡；15-考勤之本月异常考勤；16-考勤之排班提醒；20-审批之审批提醒；30-提成之提成更新提醒；40-会议室之会议室预定成功
	*/
	@Column(name = "type")
	private Integer type;
	
	/**
	*logo地址
	*/
	@Column(name = "logo_url")
	private String logoUrl;
	
	/**
	*消息名称(应用名称)
	*/
	@Column(name = "name")
	private String name;
	
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
	*消息链接
	*/
	@Column(name = "link")
	private String link;
	
	/**
	*Cron表达式
	*/
	@Column(name = "cron_expression")
	private String cronExpression;
	
	/**
	*推送对象类型,0-全体人员；1-部门，2-个人
	*/
	@Column(name = "target_type")
	private Integer targetType;
	
	/**
	*推送对象列表，英文逗号隔开
	*/
	@Column(name = "target_list")
	private String targetList;
	
	/**
	*是否已删除。1-是，0-否
	*/
	@Column(name = "is_del")
	private Integer isDel;
	
	/**
	*数据创建人ID
	*/
	@Column(name = "create_id")
	private Long createId;
	
	/**
	*数据创建时间
	*/
	@Column(name = "create_ts")
	private Date createTs;
	
	/**
	*数据更新人ID
	*/
	@Column(name = "update_id")
	private Long updateId;
	
	/**
	*数据更新时间
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
	* 设置 cronExpression 
	* @param cronExpression Cron表达式 
	*/ 
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/** 
	* 获取 Cron表达式 
	* @return cronExpression 
	*/
	public String getCronExpression() {
		return cronExpression;
	}

	/** 
	* 设置 targetType 
	* @param targetType 推送对象类型,0-全体人员；1-部门，2-个人 
	*/ 
	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	/** 
	* 获取 推送对象类型,0-全体人员；1-部门，2-个人 
	* @return targetType 
	*/
	public Integer getTargetType() {
		return targetType;
	}

	/** 
	* 设置 targetList 
	* @param targetList 推送对象列表，英文逗号隔开 
	*/ 
	public void setTargetList(String targetList) {
		this.targetList = targetList;
	}

	/** 
	* 获取 推送对象列表，英文逗号隔开 
	* @return targetList 
	*/
	public String getTargetList() {
		return targetList;
	}

	/** 
	* 设置 isDel 
	* @param isDel 是否已删除。1-是，0-否 
	*/ 
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	/** 
	* 获取 是否已删除。1-是，0-否 
	* @return isDel 
	*/
	public Integer getIsDel() {
		return isDel;
	}

	/** 
	* 设置 createId 
	* @param createId 数据创建人ID 
	*/ 
	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	/** 
	* 获取 数据创建人ID 
	* @return createId 
	*/
	public Long getCreateId() {
		return createId;
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

	/** 
	* 设置 updateId 
	* @param updateId 数据更新人ID 
	*/ 
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	/** 
	* 获取 数据更新人ID 
	* @return updateId 
	*/
	public Long getUpdateId() {
		return updateId;
	}

	/** 
	* 设置 updateTs 
	* @param updateTs 数据更新时间 
	*/ 
	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

	/** 
	* 获取 数据更新时间 
	* @return updateTs 
	*/
	public Date getUpdateTs() {
		return updateTs;
	}

	
}