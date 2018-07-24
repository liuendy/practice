package com.ybwh.springboot2.order.dao;

import java.util.List;

import com.ybwh.springboot2.order.model.Order;

public interface OrderDao {

    int deleteByPrimaryKey(Long orderId);


    int insertSelective(Order record);


    Order selectByPrimaryKey(Long orderId);


    int updateByPrimaryKeySelective(Order record);


    List<Order> selectByUserId(long UserId);
}