package com.ybwh.springboot2.common.mybatis.plugin.update;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;

/**
 * 获取带?占位符的sql及对应顺序参数的拦截器
 *
 */
@Intercepts({ @Signature(type = ParameterHandler.class, method = "setParameters", args = { PreparedStatement.class }) })
public class SqlParamterInterceptor implements Interceptor {
	
	private static final Set<String> targetTables = new HashSet<>();
	static {
		targetTables.add("book");
	}
	

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		Object target = invocation.getTarget();
		if (!(target instanceof DefaultParameterHandler)) {// mybatis中ParameterHandler只有一个实现类DefaultParameterHandler
			return invocation.proceed();
		}

		DefaultParameterHandler hander = (DefaultParameterHandler) target;

		// //获取DefaultParameterHandler中的5个属性
		MappedStatement mappedStatement = getMappedStatement(hander);
		SqlCommandType sqlType = mappedStatement.getSqlCommandType();
		if (!SqlCommandType.UPDATE.equals(sqlType) && !SqlCommandType.INSERT.equals(sqlType)
				&& !SqlCommandType.DELETE.equals(sqlType)) {//只处理更新操作
			return invocation.proceed();	
		}

		BoundSql boundSql = getBoundSql(hander);
		String sql = boundSql.getSql();
		
		String tableName = getTableName(sql);
		if(!targetTables.contains(tableName)) {//只处理目标表
			return invocation.proceed();
		}
		
		//按顺序获取更新列
		List<String> column = getColmuns(sql);
		
		
		

		Configuration configuration = mappedStatement.getConfiguration();
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		Object parameterObject = hander.getParameterObject();

		// 存储按参数顺序解析出的参数值
		List<Object> columnValues = new ArrayList<>();

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

		System.out.println(sql);
		System.out.println(columnValues);

		// 重写ErrorContext中sql的内容,使其带上参数信息
		// ErrorContext.instance().sql(boundSql.getSql() + " parameters:" +
		// this.getParameterValueString(columnValues));
		return invocation.proceed();
	}

	private List<String> getColmuns(String sql) throws JSQLParserException {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Statement stmt = parserManager.parse(new StringReader(sql));
		
		if(stmt instanceof Update) {
			Update update = (Update) stmt;
			List<Column> columnList = update.getColumns();
//			return columnList.stream().map(mapper)
		}
		
		if(stmt instanceof Insert) {
			Insert insert = (Insert) stmt;
			List<Column> columnList =  insert.getColumns();
		}
		
		return null;
	}

	private String getTableName(String sql) throws JSQLParserException {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Statement stmt = parserManager.parse(new StringReader(sql));
		if(stmt instanceof Update) {
			Update update = (Update) stmt;
			return update.getTables().get(0).getName();
		}
		
		if(stmt instanceof Insert) {
			Insert insert = (Insert) stmt;
			return insert.getTable().getName();
		}
		
		if(stmt instanceof Delete) {
			Delete delete = (Delete) stmt;
			return delete.getTable().getName();
			
		}
		
		return null;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	private BoundSql getBoundSql(DefaultParameterHandler hander)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Class<?> clz = DefaultParameterHandler.class;
		Field f = clz.getDeclaredField("boundSql");
		f.setAccessible(true);
		BoundSql boundSql = (BoundSql) f.get(hander);
		return boundSql;
	}

	private MappedStatement getMappedStatement(DefaultParameterHandler hander)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class<?> clz = DefaultParameterHandler.class;
		Field f = clz.getDeclaredField("mappedStatement");
		f.setAccessible(true);
		MappedStatement mappedStatement = (MappedStatement) f.get(hander);
		return mappedStatement;
	}

}