package com.ybwh.springboot2.common.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BLOB字段<->java.lang.String
 *
 * @author Fan Beibei at 2014-5-15 <br/>
 *         last modefied by Fan Beibei at 2014-5-15
 */
public class BlobToStringHandler extends BaseTypeHandler<String>
{
	Logger logger = LoggerFactory.getLogger(BlobToStringHandler.class);

	private static final String CHARSET = "UTF-8";

	/**
	 * 从数据集(ResultSet)中获取可空的数据
	 *
	 * @param rs
	 *            数据集
	 * @param columnName
	 *            列名
	 * @return
	 * @throws SQLException
	 * @author Fan Beibei at 2014-5-16 <br/>
	 *         last modefied by Fan Beibei at 2014-5-16
	 */
	@Override
	public String getNullableResult(ResultSet rs, String columnName)
			throws SQLException
	{
		byte[] bytes = rs.getBytes(columnName);
		if (null != bytes)
		{
			try
			{
				return new String(bytes, CHARSET);
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
				logger.info("系统不支持  " + CHARSET + " 编码");
			}
		}
		return null;
	}

	/**
	 * 从数据集(ResultSet)中获取可空的数据
	 *
	 * @param rs
	 *            数据集
	 * @param columnIndex
	 *            列号(起始值为1)
	 * @return
	 * @throws SQLException
	 * @author Fan Beibei at 2014-5-16 <br/>
	 *         last modefied by Fan Beibei at 2014-5-16
	 */
	@Override
	public String getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException
	{
		byte[] bytes = rs.getBytes(columnIndex);
		if (null != bytes)
		{
			try
			{
				return new String(bytes, CHARSET);
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
				logger.info("系统不支持  " + CHARSET + " 编码");
			}
		}
		return null;
	}

	/**
	 * 从数据集(ResultSet)中获取可空的数据
	 * @param cs 调用存储过程的结果
	 * @param columnIndex
	 *            (起始值为1)
	 * @return
	 * @throws SQLException
	 * @author Fan Beibei at 2014-5-16 <br/>
	 *         last modefied by Fan Beibei at 2014-5-16
	 */
	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException
	{
		byte[] bytes = cs.getBytes(columnIndex);
		if (null != bytes)
		{
			try
			{
				return new String(bytes, CHARSET);
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
				logger.info("系统不支持  " + CHARSET + " 编码");
			}
		}
		return null;
	}

	/**
	 * 设置非空参数时调用
	 *
	 * @param ps
	 * @param parameterIndex
	 *            参数索引(起始值为1)
	 * @param parameter
	 *            参数值
	 * @param jdbcType
	 * @throws SQLException
	 * @author Fan Beibei at 2014-5-16 <br/>
	 *         last modefied by Fan Beibei at 2014-5-16
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int parameterIndex,
			String parameter, JdbcType jdbcType) throws SQLException
	{
		try
		{
			ps.setBytes(parameterIndex, parameter.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			logger.info("系统不支持  " + CHARSET + " 编码");
			ps.setBytes(parameterIndex, new byte[] {});
		}

	}

}
