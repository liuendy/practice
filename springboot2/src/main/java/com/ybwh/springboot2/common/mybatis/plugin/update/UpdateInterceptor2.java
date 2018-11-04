package com.ybwh.springboot2.common.mybatis.plugin.update;

import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({
    @Signature(
        type=StatementHandler.class,method="update",args={ Statement.class })
})
public class UpdateInterceptor2 implements Interceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		StatementHandler sh = (StatementHandler) invocation.getTarget();
		Statement s = (Statement) invocation.getArgs()[0];
		System.out.println(s+"\n"+sh);
		
		BoundSql boundSql = sh.getBoundSql();
		
		System.out.println(boundSql+"\n"+sh);
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
	
	protected  String getSqlId(BoundSql boundSql){
		return boundSql.getSql();
	}

}
