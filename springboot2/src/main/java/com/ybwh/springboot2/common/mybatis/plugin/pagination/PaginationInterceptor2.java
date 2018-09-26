package com.ybwh.springboot2.common.mybatis.plugin.pagination;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }),
		@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class PaginationInterceptor2 implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(PaginationInterceptor2.class);

	private static final String MYSQL = "mysql";
	private static final String ORACLE = "oracle";
	private static final String DB2 = "db2";

	private String dialect = MYSQL;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		
		if (invocation.getTarget() instanceof StatementHandler) {
			return interceptStatementHandler(invocation);
		} else if (invocation.getTarget() instanceof ResultSetHandler) {
			return interceptResultHandler(invocation);
		}
		return invocation.proceed();
	}

	
	/**
	 * 完成查询总数和返回逻辑
	 * 
	 * @param invocation
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private Object interceptResultHandler(Invocation invocation)
			throws InvocationTargetException, IllegalAccessException {

		Object result = invocation.proceed();
		DefaultPagination<?> pagination = new DefaultPagination<>();
		
		
//		int count = queryCount();
//		pagination.setTotal(count);

		return pagination;
	}

	/**
	 * 
	 * 完成分页列表查询逻辑
	 * 
	 * @param invocation
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private Object interceptStatementHandler(Invocation invocation)
			throws InvocationTargetException, IllegalAccessException {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
		
		// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环 // 可以分离出最原始的的目标类)
		while (metaStatementHandler.hasGetter("h")) {
			Object object = metaStatementHandler.getValue("h");
			metaStatementHandler = SystemMetaObject.forObject(object);
		}

		// 分离最后一个代理对象的目标类
		while (metaStatementHandler.hasGetter("target")) {
			Object object = metaStatementHandler.getValue("target");
			metaStatementHandler = SystemMetaObject.forObject(object);
		}
		
		RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
		if (rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT) {//不用分页
			return invocation.proceed();
		}
		

//		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");

		// 分页参数作为参数对象parameterObject的一个属性
		String querySql = boundSql.getSql();
		String sqlId = (String) metaStatementHandler.getValue("delegate.mappedStatement.id");
		// 重写sql
		String pageSql = buildPageSql(querySql, sqlId, rowBounds);
		// 重写分页sql
		metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
		metaStatementHandler.setValue("delegate.rowBounds", new RowBounds(0,rowBounds.getLimit()));
		// 将执行权交给下一个拦截器
		Object result = invocation.proceed();
		 return result;
	}

	/**
	 * 重写sql
	 * 
	 * @param querySql
	 * @param sqlId
	 * @param rowBounds
	 * @return
	 */
	private String buildPageSql(String querySql, String sqlId, RowBounds rowBounds) {

		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();

		// 获取SQL语句

		if (null == querySql || "".equals(querySql.trim())) {
			logger.info("the sql whose id is  " + sqlId + " is blank!");
			throw new NullPointerException("the sql whose id is  " + sqlId + " is blank!");
		}

		if (rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT) {
			return querySql;
		}

		// 拼SQL
		if (ORACLE.equals(dialect.toLowerCase())) {
			int minRn = offset;
			int maxRn = offset + limit;
			querySql = "select * from (select tp9999999999999.*,rownum rn from (" + querySql
					+ ") tp9999999999999 where rownum <= " + maxRn + ") where rn>=" + minRn;
		}

		if (MYSQL.equals(dialect.toLowerCase())) {
			querySql = querySql + "  limit  " + offset + "," + limit;
		}

		if (DB2.equals(dialect.toLowerCase())) {
			int startNumber = offset;
			int endNumber = offset + limit;
			querySql = "select * from (select B9999999.* ,rownumber() over() as rn  from (" + querySql
					+ ") as B9999999) as A9999999 where A9999999.rn between " + startNumber + " and " + endNumber;
		}

		return querySql;
	}

	/**
	 * * 只拦截这两种类型的 * <br>
	 * StatementHandler * <br>
	 * ResultSetHandler * @param target * @return
	 */
	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
		// 从配置文件获取数据库方言名称
		if (null != properties.getProperty("dialect") && !"".equals(properties.getProperty("dialect"))) {
			dialect = properties.getProperty("dialect");
		}
	}

}
