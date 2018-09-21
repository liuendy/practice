package com.ybwh.springboot2.common.mybatis.plugin;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拦截mybatis的ResultSetHandler类的handleResultSets，在jdbc返回结果集之后mybatis组装结果对象之前执行。
 * 用来实现对员工的身份证、手机号完整性验证。
 * 由于项目中的jdbc的url都没配置allowMultiQueries=true,所以不存在一次查询有多个结果集的情况。
 * 于是这里只处理了一次查询有单个结果集的情况，对于一次查询有多个结果集的情况没处理。
 *
 *
 * @author: Fan Beibei
 * @date: 2018/7/3 10:49
 * @Modified By:
 */
@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class ResultSetInterceptor implements Interceptor {

	private static Logger logger = LoggerFactory.getLogger(ResultSetInterceptor.class);

	/**
	 * 不需要处理的sql语句ID
	 */
	private static final Set<String> notProcessSqlId = new HashSet<>();

	static {
		notProcessSqlId.add("com.shangde.ehr.staffachive.dao.EmployeeInfoNewDAO.selectPersonBaseInfo");// 个人中心我的档案查看
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		DefaultResultSetHandler resultSetHandler = (DefaultResultSetHandler) invocation.getTarget();// mybtis中ResultSetHandler只有一个实现类DefaultResultSetHandler
		/**
		 * 判断该sql是否是不需要进行字段完整性权限过滤的
		 */
		String sqlId = getSqlId(resultSetHandler);

		// System.out.println("****"+sqlId);
		if (notProcessSqlId.contains(sqlId)) {
			return invocation.proceed();
		}

		/**
		 * 不包含要过滤的表
		 */
		String sql = getRawSql(resultSetHandler);

		// System.out.println("****"+sql);
		// if(sqlId.contains("getStaffExport")){
		// System.out.println("****"+sql);
		// }
		// System.out.println("****"+sql);
		if (!sql.contains(EMPLOYEE_TABLE) && !sql.contains(EMPLOYEE_TABLE_BK)) {
			return invocation.proceed();
		}

		return proccessFullFieldAuth(invocation);
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	/**
	 * 员工证件号码的数据库列名称(由于命名不规范，证件号码有2个列名)
	 */
	private static final String EMPLOYEE_ID_NUM = "id_num";
	private static final String EMPLOYEE_IDNUM = "idnum";
	/**
	 * 员工手机号码的数据库列名称
	 */
	private static final String EMPLOYEE_TEL = "tel";
	/**
	 * 员工紧急联系人方式
	 */
	private static final String EMERGENCY_TEL = "emergency_tel";

	/**
	 * 员工信息数据表
	 */
	private static final String EMPLOYEE_TABLE = "t_employee_base_info";
	/**
	 * 历史记录的表 t_employee_base_info_bk*
	 */
	private static final String EMPLOYEE_TABLE_BK = "t_employee_base_info_bk";

	/**
	 * 完整手机号
	 */
	private static final String TEL = "TEL";
	/**
	 * 完整身份证号
	 */
	private static final String ID_NUM = "ID_NUM";

	/**
	 * 字段完整性权限处理
	 * 
	 * <pre>
	 * 完整手机号:
	 * 1.对员工的手机号、紧急联系人打码进行处理,即对t_employee_base_info表的tel、emergency_tel字段验证权限打码
	 * 2.对员工历史记录的手机号、紧急联系人打码进行处理,即对表t_employee_base_info_bk* 的tel、emergency_tel字段验证权限打码
	 *
	 * 完整身份证号:
	 * 1.对员工的证件号打码进行处理,即对t_employee_base_info表的id_num字段验证权限打码
	 * 2.对员工历史记录的证件号打码进行处理,即对表t_employee_base_info_bk* 的id_num字段验证权限打码
	 * </pre>
	 */
	private Object proccessFullFieldAuth(Invocation invocation) throws Throwable {
		DelegateStatement sd = new DelegateStatement((Statement) invocation.getArgs()[0]);
		UpdateableResultSet rs1 = (UpdateableResultSet) sd.getResultSet();

		int[] idNumIndexes = getColumnIndexes(rs1, EMPLOYEE_IDNUM);
		int[] id_numIndexes = getColumnIndexes(rs1, EMPLOYEE_ID_NUM);
		int[] telIndexes = getColumnIndexes(rs1, EMPLOYEE_TEL);
		int[] emergencyTelIndexes = getColumnIndexes(rs1, EMERGENCY_TEL);

		if ((null != idNumIndexes || null != id_numIndexes || null != telIndexes) || null != emergencyTelIndexes) {// 查询中有列id_num或tel

			// 查询出当前用户拥有完整性字段权限的列表
			Set<String> fullColumnAuthFields = null;
			if (null == fullColumnAuthFields) {
				fullColumnAuthFields = new HashSet<String>();
			}

			while (rs1.next()) {
				if (null != idNumIndexes && !fullColumnAuthFields.contains(ID_NUM)) {// 把身份证号打码
					updateIdnumColumnVaule(idNumIndexes, rs1);
				}

				if (null != id_numIndexes && !fullColumnAuthFields.contains(ID_NUM)) {// 把身份证号打码
					updateIdnumColumnVaule(id_numIndexes, rs1);
				}

				if (null != telIndexes && !fullColumnAuthFields.contains(TEL)) {// 把手机号打码
					updateTelColumnVaule(telIndexes, rs1);
				}

				if (null != emergencyTelIndexes && !fullColumnAuthFields.contains(TEL)) {// 给紧急联系人手机号打码
					updateTelColumnVaule(emergencyTelIndexes, rs1);
				}
			}

			rs1.resetCursor();// 将结果集游标还原，不然handleResultSets方法得不到数据

			return new Invocation(invocation.getTarget(), invocation.getMethod(), new Object[] { sd }).proceed();// 替换掉handleResultSets方法的参数
		}

		return invocation.proceed();
	}

	/**
	 * 更新电话列的数据
	 *
	 * @param telIndexes
	 * @param rs1
	 * @throws SQLException
	 */
	private void updateTelColumnVaule(int[] telIndexes, UpdateableResultSet rs1) throws SQLException {
		for (int index : telIndexes) {
			String tel = rs1.getString(index);

			if (null != tel && !"".equals(tel.trim())) {
				rs1.updateString(index, EmployeeInfoMarkUtils.markTel(tel));
			}
		}
	}

	/**
	 * 更新身份证列的数据
	 *
	 * @param idNumIndexes
	 * @param rs1
	 * @throws SQLException
	 */
	private void updateIdnumColumnVaule(int[] idNumIndexes, UpdateableResultSet rs1) throws SQLException {
		for (int index : idNumIndexes) {
			String idNum = rs1.getString(index);

			if (null != idNum && !"".equals(idNum.trim())) {
				rs1.updateString(index, EmployeeInfoMarkUtils.markIdNum(idNum));
			}
		}
	}

	/**
	 * 根据列名找列的索引号，索引号从1开始
	 *
	 * @param rs
	 * @param columnName
	 *            数据表列的名称，不是sql语句中的别名
	 * @return
	 */
	private int[] getColumnIndexes(UpdateableResultSet rs, String columnName) {
		int[] index = null;
		try {
			index = rs.findColumnName(columnName);
		} catch (SQLException e) {// 找不到就抛异常
		}

		return index;
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