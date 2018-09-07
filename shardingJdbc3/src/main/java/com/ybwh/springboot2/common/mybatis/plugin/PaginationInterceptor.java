package com.ybwh.springboot2.common.mybatis.plugin;

import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.LoggerFactory;

/**
 * 解决分页查询问题<br/>
 * 
 * (一般开发过程中数据库都不会改变，我个人认为直接用SQL语句来分页就行了，没必要用个拦截器来实现分页)
 * 
 * @author Fan Beibei at 2014-5-19 <br/>
 *         last modefied by Fan Beibei at 2014-5-19
 */
@Intercepts({
		@Signature(type = Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class }),
		@Signature(type = Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class, CacheKey.class, BoundSql.class }) })
public class PaginationInterceptor implements Interceptor
{
	private static org.slf4j.Logger logger= LoggerFactory.getLogger(PaginationInterceptor.class);


	private static final String MYSQL = "mysql";
	private static final String ORACLE = "oracle";
	private static final String DB2 = "db2";

	private String dialect = "";


	@Override
	public Object intercept(Invocation invocation) throws Throwable
	{
		if (null == dialect || "".equals(dialect.trim()))
		{
			throw new Exception("null dialect");
		}

		// 获取被拦截方法的所有参数
		Object[] args = invocation.getArgs();

		MappedStatement ms = (MappedStatement) args[0];
		Object queryParameter = args[1];
		RowBounds rowBounds = (RowBounds) args[2];

		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();

		// 获取SQL语句
		String sqlId = ms.getId();
		BoundSql boundSql = ms.getBoundSql(queryParameter);
		String querySql = boundSql.getSql();
		if (null == querySql  || "".equals(querySql.trim()))
		{
			logger.info("the sql whose id is  " + sqlId + " is blank!");
			throw new NullPointerException("the sql whose id is  " + sqlId
					+ " is blank!");
		}

		if (rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT)
		{
			return invocation.proceed();
		}

		// 拼SQL
		if (ORACLE.equals(dialect.toLowerCase()))
		{
			int minRn = offset;
			int maxRn = offset + limit;
			querySql = "select * from (select tp9999999999999.*,rownum rn from ("
					+ querySql
					+ ") tp9999999999999 where rownum <= "
					+ maxRn
					+ ") where rn>=" + minRn;
		}

		if (MYSQL.equals(dialect.toLowerCase()))
		{
			querySql = querySql + "  limit  " + offset + "," + limit;
		}

		if (DB2.equals(dialect.toLowerCase()))
		{
			int startNumber = offset;
			int endNumber = offset + limit;
			querySql = "select * from (select B9999999.* ,rownumber() over() as rn  from ("
					+ querySql
					+ ") as B9999999) as A9999999 where A9999999.rn between "
					+ startNumber + " and " + endNumber;
		}

		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), querySql,
				boundSql.getParameterMappings(), boundSql.getParameterObject());
		MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSource(
				newBoundSql));
		// 覆盖第一个参数
		invocation.getArgs()[0] = newMs;
		//重置offset
		invocation.getArgs()[2] = new RowBounds(0,limit);

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target)
	{
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties)
	{
		// 从配置文件获取数据库方言名称
		dialect = properties.getProperty("dialect");
	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms,
			SqlSource newSqlSource)
	{
		Builder builder = new Builder(ms.getConfiguration(),
				ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());

		// 坑爹啊，原来版本的keyProperty参数是String[],到这个版本就成了String了，害得我自己拼字符串
		// builder.keyProperty(ms.getKeyProperty());
		StringBuffer propertiesStr = new StringBuffer("");
		if (null != ms.getKeyProperties() && 0 != ms.getKeyProperties().length)
		{
			for (String prop : ms.getKeyProperties())
			{
				propertiesStr.append(",").append(prop);
			}
			propertiesStr.substring(1);
		}
		builder.keyProperty(propertiesStr.toString());

		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		MappedStatement newMs = builder.build();
		return newMs;
	}

	public static class BoundSqlSource implements SqlSource
	{
		BoundSql boundSql;

		public BoundSqlSource(BoundSql boundSql)
		{
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject)
		{
			return boundSql;
		}
	}

}
