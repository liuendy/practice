package com.ybwh.sqlparser.druid;


import java.util.List;

import org.junit.Test;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLVariantRefExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource.JoinType;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;

public class TestDruidSqlParser {
	@Test
	public void testSingleTableQuery() {
		String sql = "select "
				+ "id, area_name  areaName, parent_id, short_name, area_code, level, sort, id_path, name_path,is_leaf, is_del, create_time, create_id, update_time, update_id "
				+ "from area " + "where id = ? and level = ?  and area_code = ?";
		String dbType = JdbcConstants.MYSQL;

		// 格式化输出
		// String result = SQLUtils.format(sql, dbType);
		// System.out.println(result); // 缺省大写格式

		List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

		// 解析出的独立语句的个数
		System.out.println("size is:" + stmtList.size());

		for (int i = 0; i < stmtList.size(); i++) {
			SQLStatement stmt = stmtList.get(i);

			// SQLSelectStatement就是select语句解析树
			SQLSelectStatement selectStmt = (SQLSelectStatement) stmt;
			SQLSelectQuery sqlSelectQuery = selectStmt.getSelect().getQuery();
			if (sqlSelectQuery instanceof MySqlSelectQueryBlock) {
				MySqlSelectQueryBlock mysqlSelectQuery = (MySqlSelectQueryBlock) selectStmt.getSelect().getQuery();

				// 获取列
				List<SQLSelectItem> columns = mysqlSelectQuery.getSelectList();
				for (SQLSelectItem item : columns) {
					System.out.println("alias=" + item.getAlias() + ",column=" + item.getExpr());

				}
				// 这里就可以修改列
				// columns.remove();

				// 这里是单表查询，所以是SQLExprTableSource
				SQLExprTableSource from = (SQLExprTableSource) mysqlSelectQuery.getFrom();
				SQLIdentifierExpr table = (SQLIdentifierExpr) from.getExpr();
				System.out.println("table=" + table);
				// 修改表名
				from.setExpr(from.getExpr() + "222");

				// 获取where条件树
				SQLBinaryOpExpr where = (SQLBinaryOpExpr) mysqlSelectQuery.getWhere();
				SQLExpr left = where;

				for (;;) {
					/**
					 * left 为 SQLBinaryOpExpr 说明不止一个条件
					 */
					if (left instanceof SQLBinaryOpExpr) {
						SQLBinaryOpExpr left0 = (SQLBinaryOpExpr) left;
						left = left0.getLeft();
						SQLBinaryOpExpr right = (SQLBinaryOpExpr) left0.getRight();
						System.out.println("left=[" + left + "],right=[" + right+"]");
						
						// 这里可以修改where条件
//						left0.setLeft(new SQLBinaryOpExpr(new SQLIdentifierExpr("short_name"),
//								SQLBinaryOperator.Equality, new SQLVariantRefExpr("?")));
					}
					/**
					 * 可以看出where条件解析顺序是由右往左解析，每次将where条件解析为左右两个表达式，然后再解析左表达式
					 */
				}

			}

			System.out.println(selectStmt.toString());
		}

	}

	@Test
	public void testJoinQuery() {
		String sql = "select * from emp_table t1,order t2 where t1.id=t2.id";
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
			SQLSelectQuery sqlSelectQuery = selectStmt.getSelect().getQuery();
			if (sqlSelectQuery instanceof MySqlSelectQueryBlock) {
				MySqlSelectQueryBlock mysqlSelectQuery = (MySqlSelectQueryBlock) selectStmt.getSelect().getQuery();
				SQLLimit limit = new SQLLimit();
				limit.setOffset(0);
				limit.setRowCount(10);
				mysqlSelectQuery.setLimit(limit);

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

	@Test
	public void testMutiSqlQuery() {
		String sqls = "select ID from BCP_Prize; select name from BCP_Prize";
		MySqlStatementParser parser = new MySqlStatementParser(sqls);
		List<SQLStatement> stmtList = parser.parseStatementList();

		// 将AST通过visitor输出
		StringBuilder out = new StringBuilder();
		MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);

		for (SQLStatement stmt : stmtList) {
			stmt.accept(visitor);
			System.out.println(out + ";");
			out.setLength(0);
		}

	}

}
