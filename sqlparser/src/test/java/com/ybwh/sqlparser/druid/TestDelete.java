package com.ybwh.sqlparser.druid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLAllColumnExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource.JoinType;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcConstants;

public class TestDelete {
	@Test
	public void test() {
		final String dbType = JdbcConstants.MYSQL; // ORACLE or POSTGRESQL or SQL_SERVER or HIVE or ODPS

		String sql = "delete from t where id = 2 and name = 'wenshao'";
		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType);
		SQLDeleteStatement stmt = (SQLDeleteStatement) parser.parseStatement();
		assertTrue(stmt.removeCondition("name = 'wenshao'"));

		assertEquals("DELETE FROM t\n" +
		        "WHERE id = 2", stmt.toString());

		assertTrue(
		        stmt.removeCondition("id = 2"));

		assertEquals("DELETE FROM t", stmt.toString());

		stmt.addCondition("id = 3");
		assertEquals("DELETE FROM t\n" +
		        "WHERE id = 3", stmt.toString());
	}
	
	
	@Test
	public void testJoinQuery() {
		String sql = "select *,t1.*,t1.id,t2.id from emp_table t1,order t2 where t1.id=t2.id order by t1.id desc limit 20,10";
		String dbType = JdbcConstants.MYSQL;

		// 格式化输出
		String result = SQLUtils.format(sql, dbType);
		System.out.println(result); // 缺省大写格式
		List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

		// 解析出的独立语句的个数
		System.out.println("size is:" + stmtList.size());
		for (int i = 0; i < stmtList.size(); i++) {
			SQLStatement stmt = stmtList.get(i);
			MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
			stmt.accept(visitor);

			SQLSelectStatement selectStmt = (SQLSelectStatement) stmt;
		
			SQLSelect select = selectStmt.getSelect();
			
			
			
			
			
			
			SQLOrderBy orderBy = select.getOrderBy();
			System.out.println("orderBy:"+orderBy);
			SQLExpr offset = select.getOffset();
			System.out.println("offset:"+offset);
			SQLExpr rowCount = select.getRowCount();
			System.out.println("rowCount:"+rowCount);
			
			
			
			

			
			SQLSelectQuery sqlSelectQuery = select.getQuery();
			
			if (sqlSelectQuery instanceof MySqlSelectQueryBlock) {
				MySqlSelectQueryBlock mysqlSelectQuery = (MySqlSelectQueryBlock) selectStmt.getSelect().getQuery();
				
				SQLLimit limit = mysqlSelectQuery.getLimit();
				System.out.println("limit:"+limit.getOffset()+","+limit.getRowCount());
				SQLLimit limit2 = new SQLLimit();
				limit.setOffset(0);
				limit.setRowCount(10);
				mysqlSelectQuery.setLimit(limit2);
				
				
//				mysqlSelectQuery.addSelectItem(expr);
//				mysqlSelectQuery.replace(expr, target)
//				
				
//				mysqlSelectQuery.getWhere();
//				mysqlSelectQuery.addWhere(condition);
//				mysqlSelectQuery.getSortBy()
//				mysqlSelectQuery.addSortBy(item);
//				mysqlSelectQuery.addCondition(conditionSql);
//				mysqlSelectQuery.removeCondition(condition)
				
				
				
				
				// 获取列
				List<SQLSelectItem> columns = mysqlSelectQuery.getSelectList();
				for (SQLSelectItem item : columns) {
					String alias = item.getAlias();
					String columnExpr = null;
					String columnName=null;
					String tableName = null;
					
					
					if(item.getExpr() instanceof SQLPropertyExpr) {
						SQLPropertyExpr expr = (SQLPropertyExpr) item.getExpr();
						columnExpr = expr.toString();
						columnName = expr.getName();
						
						SQLExprTableSource tableSource = (SQLExprTableSource) expr.getResolvedTableSource();
						SQLIdentifierExpr table = (SQLIdentifierExpr) tableSource.getExpr();
						tableName = table.getName();
					}
					
					if(item.getExpr() instanceof SQLAllColumnExpr) {
						SQLAllColumnExpr expr = (SQLAllColumnExpr) item.getExpr();
						columnExpr = "*";
						columnName = "*";
						SQLJoinTableSource tableSource = (SQLJoinTableSource) expr.getResolvedTableSource();
						tableName = tableSource.toString();
					}
					
					
					
					System.out.println("alias=" + alias+ ",column=" + columnExpr+",columnName="+columnName+",tableName="+tableName);
					
				}
				
				
				

				// 这里是连接查询，所以是SQLJoinTableSource
				SQLJoinTableSource from = (SQLJoinTableSource) mysqlSelectQuery.getFrom();
				// 连接类型
				JoinType joinType = from.getJoinType();
				System.out.println("joinType=" + joinType);

				//
				SQLExprTableSource left = (SQLExprTableSource) from.getLeft();
				SQLExprTableSource right = (SQLExprTableSource) from.getRight();
				System.out.println("left=" + left.getExpr() + ",right=" + right.getName());
				// 修改表名
				left.setExpr(left.getExpr() + "2");
				System.out.println("left=" + left.getExpr() + ",right=" + right.getName());

			}
			// 获取操作方法名称,依赖于表名称
			System.out.println("Manipulation : " + visitor.getTables());

			// 获取字段名称
			System.out.println("fields : " + visitor.getColumns());
			System.out.println(stmt.toString());

		}
	}

}
