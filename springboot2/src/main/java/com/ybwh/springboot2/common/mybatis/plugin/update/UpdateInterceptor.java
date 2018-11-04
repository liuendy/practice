package com.ybwh.springboot2.common.mybatis.plugin.update;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({
    @Signature(
        type=StatementHandler.class,method="update",args={ Statement.class })
})
public class UpdateInterceptor implements Interceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		StatementHandler sh = (StatementHandler) invocation.getTarget();
		Statement stmt = (Statement) invocation.getArgs()[0];
		
		BoundSql boundSql = sh.getBoundSql();
		
		System.out.println("sql:"+getSql(boundSql));
		
		List<ParameterMapping> params = getParameterMapping(boundSql);
		if(null != params &&  params.size() > 0){
			for(ParameterMapping pm :params){
				System.out.println(pm.getExpression());
			}
		}
		

		
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
	}
	
	
	protected  String getSql(BoundSql boundSql){
		return boundSql.getSql();
	}
	

	
	private List<ParameterMapping> getParameterMapping(BoundSql boundSql){
        return boundSql.getParameterMappings();
    }

}
