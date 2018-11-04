package com.ybwh.springboot2.common.mybatis.plugin.update;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({
    @Signature(
        type=Executor.class,method="update",args={ MappedStatement.class,Object.class })
})
public class UpdateInterceptor implements Interceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		Executor executor = (Executor) invocation.getTarget();
		MappedStatement st = (MappedStatement) invocation.getArgs()[0];
		Object param = invocation.getArgs()[1];
		
		
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
	}
	
	
	/**
	 * 获取mybatis的sqlId
	 *
	 * @param resultSetHandler
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String getSqlId(DefaultResultSetHandler resultSetHandler)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field mappedStatementField = DefaultResultSetHandler.class.getDeclaredField("mappedStatement");
		mappedStatementField.setAccessible(true);
		MappedStatement mappedStatement = (MappedStatement) mappedStatementField.get(resultSetHandler);
		return mappedStatement.getId();
	}

	/**
	 * 获取原始的sql
	 *
	 * @param resultSetHandler
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String getRawSql(DefaultResultSetHandler resultSetHandler)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field boundSqlField = DefaultResultSetHandler.class.getDeclaredField("boundSql");
		boundSqlField.setAccessible(true);
		BoundSql boundSql = (BoundSql) boundSqlField.get(resultSetHandler);
		return boundSql.getSql();
	}
	
	
	/**
	 * 
	 * 获取sql参数
	 * 
	 * @param resultSetHandler
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private Map<String,Object> getSqlParameterMap(DefaultResultSetHandler resultSetHandler) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        Field boundSqlField = DefaultResultSetHandler.class.getDeclaredField("boundSql");
        boundSqlField.setAccessible(true);
        BoundSql boundSql = (BoundSql) boundSqlField.get(resultSetHandler);

        return (Map<String,Object>) boundSql.getParameterObject();
    }

}
