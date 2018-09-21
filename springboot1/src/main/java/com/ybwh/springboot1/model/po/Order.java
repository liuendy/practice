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
@Table(name = "t_order", uniqueConstraints = { @UniqueConstraint(columnNames = { "order_id" }) })
public class Order {

	/**
	*主键
	*/
	@Id
	@SequenceGenerator(name = "t_order")
	@Column(name = "order_id")
	private Long orderId;
	
	/**
	*用户ID
	*/
	@Column(name = "user_id")
	private Long userId;
	
	/**
	*下单时间
	*/
	@Column(name = "order_time")
	private Date orderTime;
	
	/** 
	* 设置 orderId 
	* @param orderId 主键 
	*/ 
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/** 
	* 获取 主键 
	* @return orderId 
	*/
	public Long getOrderId() {
		return orderId;
	}

	/** 
	* 设置 userId 
	* @param userId 用户ID 
	*/ 
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/** 
	* 获取 用户ID 
	* @return userId 
	*/
	public Long getUserId() {
		return userId;
	}

	/** 
	* 设置 orderTime 
	* @param orderTime 下单时间 
	*/ 
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/** 
	* 获取 下单时间 
	* @return orderTime 
	*/
	public Date getOrderTime() {
		return orderTime;
	}

	
}