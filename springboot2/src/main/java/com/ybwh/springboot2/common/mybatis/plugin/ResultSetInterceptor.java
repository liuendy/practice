package com.ybwh.springboot2.common.mybatis.plugin;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
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

    public static Logger logger = LoggerFactory
            .getLogger(ResultSetInterceptor.class);


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return proccessIdnumAndTel(invocation);
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
     * 不需要处理的sql语句ID
     */
    private static final Set<String> notProcessSqlId = new HashSet<>();
    static {
        notProcessSqlId.add("com.shangde.ehr.staffachive.dao.EmployeeInfoNewDAO.selectPersonBaseInfo");//个人中心我的档案查看
    }

    /**
     * 对员工的证件号和手机号打码进行处理
     */
    private Object proccessIdnumAndTel(Invocation invocation) throws Throwable {
        //mybatis里 ResultSetHandler只有 DefaultResultSetHandler一个实现类
        DefaultResultSetHandler  resultSetHandler= (DefaultResultSetHandler) invocation.getTarget();

        Object[] args = invocation.getArgs();
        // 获取到当前的Statement
        Statement stmt = (Statement) args[0];
       

        DelegateStatement sd = new DelegateStatement(stmt);
        UpdateableResultSet rs1 = (UpdateableResultSet) sd.getResultSet();

        int idNumIndex = getColumnIndex(rs1,EMPLOYEE_IDNUM);
        int id_numIndex = getColumnIndex(rs1,EMPLOYEE_ID_NUM);
        int telIndex = getColumnIndex(rs1,EMPLOYEE_TEL);
        int emergencyTelIndex = getColumnIndex(rs1,EMERGENCY_TEL);

        if ((-1 != idNumIndex || -1 != id_numIndex || -1 != telIndex ) || -1 != emergencyTelIndex) {//查询中有列id_num或tel
            String sqlId = getSqlId(resultSetHandler);
            logger.debug("proccessIdnumAndTel,idNumIndex={}，id_numIndex={},telIndex={},emergencyTelIndex={},sqlId={}",
                    idNumIndex,id_numIndex,telIndex,emergencyTelIndex,sqlId);

            if(notProcessSqlId.contains(sqlId)){
                return invocation.proceed();
            }


            //TODO 判断员工有无权限访问两个字段
            if(hasFullColumnAuth()){
                return invocation.proceed();
            }



            while (rs1.next()) {
                if(-1 != idNumIndex){//把身份证号打码
                    String idNum = rs1.getString(idNumIndex);

                    if (null != idNum && !"".equals(idNum.trim())) {
                        rs1.updateString(idNumIndex, EmployeeInfoMarkUtils.markIdNum(idNum));
                    }
                }

                if(-1 != id_numIndex){//把身份证号打码
                    String id_num = rs1.getString(id_numIndex);

                    if (null != id_num && !"".equals(id_num.trim())) {
                        rs1.updateString(id_numIndex, EmployeeInfoMarkUtils.markIdNum(id_num));
                    }
                }


                if(-1 != telIndex){//把手机号打码
                    String tel = rs1.getString(telIndex);
                    if (null != tel && !"".equals(tel.trim())) {
                        rs1.updateString(telIndex, EmployeeInfoMarkUtils.markTel(tel));
                    }
                }


                if(-1 != emergencyTelIndex){//给紧急联系人手机号打码
                    String emergencyTel = rs1.getString(emergencyTelIndex);
                    if (null != emergencyTel && !"".equals(emergencyTel.trim())) {
                        rs1.updateString(telIndex, EmployeeInfoMarkUtils.markTel(emergencyTel));
                    }
                }

            }

            rs1.resetCursor();//将结果集游标还原，不然handleResultSets方法得不到数据

            return new Invocation(invocation.getTarget(), invocation.getMethod(), new Object[]{sd}).proceed();//替换掉handleResultSets方法的参数
        }

        return invocation.proceed();
    }

    private boolean hasFullColumnAuth() {
        //TODO
        return false;
    }


    /**
     * 根据列名找列的索引号，索引号从1开始
     *
     * @param rs
     * @param columnName 数据表列的名称，不是sql语句中的别名
     * @return
     */
    private int getColumnIndex(UpdateableResultSet rs,String columnName) {
        int index = -1;
        try {
            index = rs.findColumnName(columnName);
        }catch (SQLException e){//找不到就抛异常
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

}