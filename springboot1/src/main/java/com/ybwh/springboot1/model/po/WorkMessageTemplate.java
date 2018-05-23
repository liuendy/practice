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
@Table(name = "t_work_message_template", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class WorkMessageTemplate {

	/**
	*主键
	*/
	@Id
	@SequenceGenerator(name = "t_work_message_template")
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
	*消息内容模板
	*/
	@Column(name = "content_template")
	private String contentTemplate;
	
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
	* 设置 contentTemplate 
	* @param contentTemplate 消息内容模板 
	*/ 
	public void setContentTemplate(String contentTemplate) {
		this.contentTemplate = contentTemplate;
	}

	/** 
	* 获取 消息内容模板 
	* @return contentTemplate 
	*/
	public String getContentTemplate() {
		return contentTemplate;
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