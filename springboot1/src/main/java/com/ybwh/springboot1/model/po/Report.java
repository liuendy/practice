package com.ybwh.springboot1.model.po;

import java.math.BigDecimal;
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
@Table(name = "t_report", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Report {

	/**
	*主键
	*/
	@Id
	@SequenceGenerator(name = "t_report")
	@Column(name = "id")
	private Long id;
	
	/**
	*总销量
	*/
	@Column(name = "sale_count")
	private Integer saleCount;
	
	/**
	*销售额
	*/
	@Column(name = "sale_amount")
	private BigDecimal saleAmount;
	
	/**
	*创建时间
	*/
	@Column(name = "create_time")
	private Date createTime;
	
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
	* 设置 saleCount 
	* @param saleCount 总销量 
	*/ 
	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}

	/** 
	* 获取 总销量 
	* @return saleCount 
	*/
	public Integer getSaleCount() {
		return saleCount;
	}

	/** 
	* 设置 saleAmount 
	* @param saleAmount 销售额 
	*/ 
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	/** 
	* 获取 销售额 
	* @return saleAmount 
	*/
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	/** 
	* 设置 createTime 
	* @param createTime 创建时间 
	*/ 
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/** 
	* 获取 创建时间 
	* @return createTime 
	*/
	public Date getCreateTime() {
		return createTime;
	}

	
}