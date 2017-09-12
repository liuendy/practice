package com.dao;

import com.entity.SimCard;

public interface SimCardMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(SimCard record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(SimCard record);

    /**
     *
     * @mbggenerated
     */
    SimCard selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SimCard record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SimCard record);
}