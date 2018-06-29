package com.ybwh.springboot2.common.mybatis.plugin;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class ResultSetInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		// 获取到当前的Statement
		Statement stmt = (Statement) args[0];
		// 通过Statement获得当前结果集
		ResultSet rs0 = stmt.getResultSet();
		ResultSetMetaData meta = rs0.getMetaData();
		
		
		
		

		String areaNameColum = "area_name";
		int areaNameIndex = -1;

		// 找到目标列的索引
		for (int i = 0; i < meta.getColumnCount(); i++) {
			if (areaNameColum.equals(meta.getColumnName(i + 1).toLowerCase())) {
				areaNameIndex = i + 1;
			}

			if (-1 != areaNameIndex) {
				break;
			}
		}

		if (-1 != areaNameIndex) {
			//mybatis里 ResultSetHandler只有 DefaultResultSetHandler一个实现类   
			DefaultResultSetHandler  resultSetHandler= (DefaultResultSetHandler) invocation.getTarget();         
			System.out.println("$$$$$$$$$$$$$$$$$$$$$sqlId="+getSqlId(resultSetHandler));

			DelegateStatement sd = new DelegateStatement(stmt);

			UpdateableResultSet rs1 = (UpdateableResultSet) sd.getResultSet();

			while (rs1.next()) {
				String areaName = rs1.getString(areaNameIndex);
				rs1.updateString(areaNameIndex, areaName + "222222222222222222");
			}

			rs1.resetCursor();

			return new Invocation(invocation.getTarget(), invocation.getMethod(), new Object[] { sd }).proceed();

		}

		return invocation.proceed();
	}

	private String getSqlId(DefaultResultSetHandler resultSetHandler) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field mappedStatementField = DefaultResultSetHandler.class.getDeclaredField("mappedStatement");
		mappedStatementField.setAccessible(true);
		MappedStatement mappedStatement = (MappedStatement) mappedStatementField.get(resultSetHandler);
		return mappedStatement.getId();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}