package com.ybwh.springboot2.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import com.ybwh.springboot2.demo.entity.Area;

public interface AreaDao {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(Area record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Area record);

    /**
     *
     * @mbggenerated
     */
    Area selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Area record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Area record);
    
    
    
    Area selectTest(Integer id);
    
    
    
    List<Area> selectPagination(Map<String, Object> param, RowBounds rowBounds);
    
    
}