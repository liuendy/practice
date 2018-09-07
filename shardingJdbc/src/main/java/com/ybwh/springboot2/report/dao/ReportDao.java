package com.ybwh.springboot2.report.dao;

import com.ybwh.springboot2.report.model.Report;

public interface ReportDao {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int insert(Report record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Report record);

    /**
     *
     * @mbggenerated
     */
    Report selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Report record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Report record);
}