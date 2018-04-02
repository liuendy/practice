package com.dao;

import com.entity.OrganizationBaseInfo;

public interface OrganizationBaseInfoMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long organizationId);

    /**
     *
     * @mbggenerated
     */
    int insert(OrganizationBaseInfo record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(OrganizationBaseInfo record);

    /**
     *
     * @mbggenerated
     */
    OrganizationBaseInfo selectByPrimaryKey(Long organizationId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OrganizationBaseInfo record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OrganizationBaseInfo record);
}