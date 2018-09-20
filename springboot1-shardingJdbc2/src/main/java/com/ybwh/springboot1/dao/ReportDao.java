package com.ybwh.springboot1.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ybwh.springboot1.jdbc.BaseDao;
import com.ybwh.springboot1.model.po.Report;


@Component
public class ReportDao extends BaseDao {

	public List<Report> selectEqCreateTime(Date createTime) {
		return query("select " + getEntityColumnSql(Report.class) + " from " + getEntityTableName(Report.class)
				+ " where create_time=?", new Object[] { createTime }, Report.class);
	}

	public List<Report> selectInCreateTime(List<Date> createTimeList) {
		String sql = "select " + getEntityColumnSql(Report.class) + " from " + getEntityTableName(Report.class)
				+ " where create_time in " + getQuestionStr(createTimeList.size());

		return query(sql, createTimeList.toArray(), Report.class);
	}

	public List<Report> selectBetweenCreateTime(Date start, Date end) {
		String sql = "select " + getEntityColumnSql(Report.class) + " from " + getEntityTableName(Report.class)
				+ " where create_time between ? and ? ";
		return query(sql, new Object[] { start, end }, Report.class);
	}

	public Integer selectCount(Date start, Date end) {
		String sql = "select count(*) from " + getEntityTableName(Report.class) + " where create_time between ? and ? ";
		return queryForObject(sql, new Object[] { start, end }, Integer.class);
	}

	public Long selectSum(Date start, Date end) {
		String sql = "select sum(id) from " + getEntityTableName(Report.class) + " where create_time between ? and ? ";
		return queryForObject(sql, new Object[] { start, end }, Long.class);
	}

}
