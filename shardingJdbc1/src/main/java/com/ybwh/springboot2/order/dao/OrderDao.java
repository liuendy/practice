package com.ybwh.springboot2.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ybwh.springboot2.order.model.Order;

public interface OrderDao {

    int deleteByPrimaryKey(Long orderId);


    int insertSelective(Order record);


    Order selectByPrimaryKey(Long orderId);


    int updateByPrimaryKeySelective(Order record);


    List<Order> selectByUserId(long UserId);


	List<Order> selectByUserIdAndOrderId(@Param("userId") long l, @Param("orderId")long m);
}