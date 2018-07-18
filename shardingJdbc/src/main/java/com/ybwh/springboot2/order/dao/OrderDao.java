package com.ybwh.springboot2.order.dao;

import com.ybwh.springboot2.order.model.Order;

public interface OrderDao {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long orderId);

    /**
     *
     * @mbggenerated
     */
    int insert(Order record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Order record);

    /**
     *
     * @mbggenerated
     */
    Order selectByPrimaryKey(Long orderId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Order record);
}