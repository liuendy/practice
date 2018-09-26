package com.ybwh.springboot2.common.mybatis.plugin.pagination;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
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
 * 解决分页查询问题,需要查询方法中带有RowBounds参数，返回类型直接写Pagination即可
 * 
 * @author Fan Beibei at 2014-5-19 <br/>
 *         last modefied by Fan Beibei at 2014-5-19
 */
@Intercepts({
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
		@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class })

})
public class PaginationInterceptor implements Interceptor {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);

	private static final String MYSQL = "mysql";
	private static final String ORACLE = "oracle";
	private static final String DB2 = "db2";

	private static final ThreadLocal<DefaultPagination<?>> paginationLocal = new ThreadLocal<>();

	private String dialect = "";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		if (target instanceof Executor) {// 拦截Executor逻辑
			return interceptQuery(invocation);

		} else if (invocation.getTarget() instanceof ResultSetHandler) {// 拦截ResultSetHandler逻辑
			return interceptResultHandler(invocation);
		}

		return invocation.proceed();
	}

	private Object interceptQuery(Invocation invocation) throws InvocationTargetException, IllegalAccessException, SQLException {
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
		
		//改为对dataSource做缓存
        DataSource dataSource = ms.getConfiguration().getEnvironment().getDataSource();

		DefaultPagination<?>  pagination = new DefaultPagination<>();
		pagination.setPageIndex(offset / limit + 1);
		pagination.setPageSize(limit);
//		Statement statement = (Statement) invocation.getArgs()[0];

		int total = queryPaginationCount(querySql, dataSource.getConnection());
		
		
		pagination.setTotal(total);
		pagination.setTotalPage(total / pagination.getPageSize() == 0 ? total / pagination.getPageSize()
				: total / pagination.getPageSize() + 1);
		
		paginationLocal.set(pagination);
		
		
		
		
		
		if (null == querySql || "".equals(querySql.trim())) {
			logger.info("the sql whose id is  " + sqlId + " is blank!");
			throw new NullPointerException("the sql whose id is  " + sqlId + " is blank!");
		}

		if (rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT) {
			return invocation.proceed();
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

		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), querySql, boundSql.getParameterMappings(),
				boundSql.getParameterObject());
		MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSource(newBoundSql));
		// 覆盖第一个参数
		invocation.getArgs()[0] = newMs;
		// 重置offset
		invocation.getArgs()[2] = new RowBounds(0, limit);
        

		return invocation.proceed();

	}

	private Object interceptResultHandler(Invocation invocation)
			throws InvocationTargetException, IllegalAccessException, SQLException {

		Object result = invocation.proceed();
		DefaultPagination<?> pagination = paginationLocal.get();
		pagination.setList((List) result);
		
		return pagination;
	}

	private int queryPaginationCount(String sql, Connection connection) {
		String countSql = "select count(0) from (" + sql + ") t";
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			countStmt = connection.prepareStatement(countSql);
			rs = countStmt.executeQuery();
			int totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			return totalCount;
		} catch (SQLException e) {
			logger.error("Ignore this exception", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception", e);
			}
			try {
				countStmt.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception", e);
			}
		}
		return -1;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// 从配置文件获取数据库方言名称
		dialect = properties.getProperty("dialect");
	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());

		// 坑爹啊，原来版本的keyProperty参数是String[],到这个版本就成了String了，害得我自己拼字符串
		// builder.keyProperty(ms.getKeyProperty());
		StringBuffer propertiesStr = new StringBuffer("");
		if (null != ms.getKeyProperties() && 0 != ms.getKeyProperties().length) {
			for (String prop : ms.getKeyProperties()) {
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

	public static class BoundSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

}
