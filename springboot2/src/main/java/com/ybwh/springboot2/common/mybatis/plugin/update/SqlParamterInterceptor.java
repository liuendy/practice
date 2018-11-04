package com.ybwh.springboot2.common.mybatis.plugin.update;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * 获取带?占位符的sql及对应顺序参数的拦截器
 *
 */
@Intercepts({ 
	@Signature(type = ParameterHandler.class, 
			method = "setParameters", 
			args = { PreparedStatement.class }) 
})
public class SqlParamterInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		Object target = invocation.getTarget();
		if (!(target instanceof DefaultParameterHandler)) {//mybatis中ParameterHandler只有一个实现类DefaultParameterHandler
			return null;
		}
		
		DefaultParameterHandler hander = (DefaultParameterHandler) target;
		
		// //获取DefaultParameterHandler中的5个属性
		Class<?> clz = hander.getClass();
		Field f = clz.getDeclaredField("mappedStatement");
		f.setAccessible(true);
		MappedStatement mappedStatement = (MappedStatement) f.get(hander);
		Configuration configuration = mappedStatement.getConfiguration();
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		f = clz.getDeclaredField("boundSql");
		f.setAccessible(true);
		BoundSql boundSql = (BoundSql) f.get(hander);
		Object parameterObject = hander.getParameterObject();
		//存储按参数顺序解析出的参数值
		List<Object> columnValues = new ArrayList<Object>();
		// 迭代得到sql参数值
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) {
						// issue #448 ask first for additional params
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					columnValues.add(value);
				}
			}
		}
		
		
		System.out.println(boundSql.getSql());
		System.out.println(columnValues);
		
		
		// 重写ErrorContext中sql的内容,使其带上参数信息
//		ErrorContext.instance().sql(boundSql.getSql() + " parameters:" + this.getParameterValueString(columnValues));
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}
}