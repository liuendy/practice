package com.ybwh.springboot2.order.model;

import java.util.Date;

public class Order {
    /**
     * 主键
     */
    private Long orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 主键
     * @return order_id 主键
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 主键
     * @param orderId 主键
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 用户ID
     * @return user_id 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 下单时间
     * @return order_time 下单时间
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * 下单时间
     * @param orderTime 下单时间
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}