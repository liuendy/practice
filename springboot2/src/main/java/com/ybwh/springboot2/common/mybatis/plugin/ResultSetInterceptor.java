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
 * 用来实现对t_employee_base_info表的身份证、手机号完整性验证。
 * 这里只处理了一个结果集的情况，对于一个sql多个结果集的情况没处理
 *
 * @author: Fan Beibei
 * @date: 2018/7/3 10:49
 * @Modified By:
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class ResultSetInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory
            .getLogger(ResultSetInterceptor.class);




    /**
     * 不需要处理的sql语句ID
     */
    private static final Set<String> notProcessSqlId = new HashSet<>();

    static {
        notProcessSqlId.add("com.shangde.ehr.staffachive.dao.EmployeeInfoNewDAO.selectPersonBaseInfo");//个人中心我的档案查看
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        DefaultResultSetHandler resultSetHandler = (DefaultResultSetHandler) invocation.getTarget();//mybatis中ResultSetHandler只有一个实现类DefaultResultSetHandler
        /**
         * 判断该sql是否是不需要进行字段完整性权限过滤的
         */
        String sqlId = getSqlId(resultSetHandler);
        if (notProcessSqlId.contains(sqlId)) {
            return invocation.proceed();
        }


        /**
         * 不包含要过滤的表
         */
        String sql = getRawSql(resultSetHandler);
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
     * 历史记录的处理
     */
    private static final String EMPLOYEE_TABLE_BK = "t_employee_base_info_bk";

    /**
     * 字段完整性权限处理
     * <p>
     * 1.对员工的证件号、手机号、紧急联系人打码进行处理,即对t_employee_base_info表的id_num、tel、emergency_tel三个字段验证权限打码
     * 2.对员工历史记录的证件号、手机号、紧急联系人打码进行处理,即对表t_employee_base_info_bk* 的id_num、tel、emergency_tel三个字段验证权限打码
     */
    private Object proccessFullFieldAuth(Invocation invocation) throws Throwable {
        DelegateStatement sd = new DelegateStatement((Statement) invocation.getArgs()[0]);
        UpdateableResultSet rs1 = (UpdateableResultSet) sd.getResultSet();

        int idNumIndex = getColumnIndex(rs1, EMPLOYEE_IDNUM);
        int id_numIndex = getColumnIndex(rs1, EMPLOYEE_ID_NUM);
        int telIndex = getColumnIndex(rs1, EMPLOYEE_TEL);
        int emergencyTelIndex = getColumnIndex(rs1, EMERGENCY_TEL);

        if ((-1 != idNumIndex || -1 != id_numIndex || -1 != telIndex) || -1 != emergencyTelIndex) {//查询中有列id_num或tel

            

            //查询出当前用户拥有完整性字段权限的列表
            Set<String> fullColumnAuthFields = new HashSet<>();//TODO 
           


            while (rs1.next()) {
                if (-1 != idNumIndex && !fullColumnAuthFields.contains(EMPLOYEE_TABLE + "." + EMPLOYEE_ID_NUM)) {//把身份证号打码
                    String idNum = rs1.getString(idNumIndex);

                    if (null != idNum && !"".equals(idNum.trim())) {
                        rs1.updateString(idNumIndex, EmployeeInfoMarkUtils.markIdNum(idNum));
                    }
                }

                if (-1 != id_numIndex && !fullColumnAuthFields.contains(EMPLOYEE_TABLE + "." + EMPLOYEE_ID_NUM)) {//把身份证号打码
                    String id_num = rs1.getString(id_numIndex);

                    if (null != id_num && !"".equals(id_num.trim())) {
                        rs1.updateString(id_numIndex, EmployeeInfoMarkUtils.markIdNum(id_num));
                    }
                }


                if (-1 != telIndex && !fullColumnAuthFields.contains(EMPLOYEE_TABLE + "." + EMPLOYEE_TEL)) {//把手机号打码
                    String tel = rs1.getString(telIndex);
                    if (null != tel && !"".equals(tel.trim())) {
                        rs1.updateString(telIndex, EmployeeInfoMarkUtils.markTel(tel));
                    }
                }


                if (-1 != emergencyTelIndex && !fullColumnAuthFields.contains(EMPLOYEE_TABLE + "." + EMPLOYEE_TEL)) {//给紧急联系人手机号打码
                    String emergencyTel = rs1.getString(emergencyTelIndex);
                    if (null != emergencyTel && !"".equals(emergencyTel.trim())) {
                        rs1.updateString(emergencyTelIndex, EmployeeInfoMarkUtils.markTel(emergencyTel));
                    }
                }

            }

            rs1.resetCursor();//将结果集游标还原，不然handleResultSets方法得不到数据

            return new Invocation(invocation.getTarget(), invocation.getMethod(), new Object[]{sd}).proceed();//替换掉handleResultSets方法的参数
        }

        return invocation.proceed();
    }


    /**
     * 根据列名找列的索引号，索引号从1开始
     *
     * @param rs
     * @param columnName 数据表列的名称，不是sql语句中的别名
     * @return
     */
    private int getColumnIndex(UpdateableResultSet rs, String columnName) {
        int index = -1;
        try {
            index = rs.findColumnName(columnName);
        } catch (SQLException e) {//找不到就抛异常
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
    private String getSqlId(DefaultResultSetHandler resultSetHandler) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

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
    private String getRawSql(DefaultResultSetHandler resultSetHandler) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field boundSqlField = DefaultResultSetHandler.class.getDeclaredField("boundSql");
        boundSqlField.setAccessible(true);
        BoundSql boundSql = (BoundSql) boundSqlField.get(resultSetHandler);
        return boundSql.getSql();
    }

}