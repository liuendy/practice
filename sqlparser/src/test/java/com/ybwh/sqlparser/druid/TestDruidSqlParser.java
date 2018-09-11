package com.ybwh.sqlparser.druid;

import java.util.List;

import org.junit.Test;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;

public class TestDruidSqlParser {
	@Test
	public void testSimple(){
//		String sql = "select t1.user_id,t2.oid from emp_table t1,order t2";
		String sql = "select * from emp_table t1,order t2";
        String dbType = JdbcConstants.MYSQL;
 
        //格式化输出
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result); // 缺省大写格式
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
 
        //解析出的独立语句的个数
        System.out.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {
            SQLStatement stmt = stmtList.get(i);
            MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
            stmt.accept(visitor);
 
            SQLSelectStatement selectStmt = (SQLSelectStatement)stmt;
    		SQLSelectQuery sqlSelectQuery = selectStmt.getSelect().getQuery();
    		if(sqlSelectQuery instanceof MySqlSelectQueryBlock) {
    			MySqlSelectQueryBlock mysqlSelectQuery = (MySqlSelectQueryBlock)selectStmt.getSelect().getQuery();
    			SQLLimit limit = new SQLLimit();
    			limit.setOffset(0);
    			limit.setRowCount(10);
    			mysqlSelectQuery.setLimit(limit);
    		}
            //获取操作方法名称,依赖于表名称
            System.out.println("Manipulation : " + visitor.getTables());
            //获取字段名称
            System.out.println("fields : " + visitor.getColumns());
            System.out.println(stmt.toString());
            
        }
	}


}
