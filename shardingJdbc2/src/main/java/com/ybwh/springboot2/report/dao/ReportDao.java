package com.ybwh.springboot2.report.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	List<Report> selectEqCreateTime(@Param("createTime") Date createTime);

	List<Report> selectInCreateTime(@Param("createTimeList") Collection<Date> createTimeList);

	List<Report> selectBetweenCreateTime(@Param("start") Date start, @Param("end") Date end);
	
	
	Integer selectCount(@Param("start") Date start, @Param("end") Date end);
	
	long selectSum(@Param("start") Date start, @Param("end") Date end);
	
	
	
}