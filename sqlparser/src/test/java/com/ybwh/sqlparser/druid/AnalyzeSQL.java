package com.ybwh.sqlparser.druid;

import com.alibaba.druid.sql.ast.SQLCommentHint;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLAggregateExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 对一条SQL 语句进行解析.获取这条SQL 的字段.条件.聚合函数.以及条数 Created by Think on 2017/3/22.
 */
public class AnalyzeSQL {

	private static String SQL = "SELECT oilwear_avg,sum(oilwear_avg) as oilConsumption,count(id) "
			+ "FROM DBName.tableName as ted WHERE filed1 LIKE '123456%' AND filed2 >= 0 "
			+ "AND filed3 = \"abcdefg\" AND filed4_ms "
			+ "between 1483200000000 and 1488297600000 group by filed5,filed6 order by filed7 LIMIT 1";

	public static void analyzeSQL(String sql) {
		MySqlStatementParser parse = new MySqlStatementParser(sql);
		SQLStatement statement = parse.parseStatement();

		if (statement instanceof SQLSelectStatement) {
			SQLSelectStatement selectStatement = (SQLSelectStatement) statement;
			SQLSelect select = selectStatement.getSelect();
			SQLSelectQuery selectQuery = select.getQuery();

			if (selectQuery instanceof MySqlSelectQueryBlock) {
				MySqlSelectQueryBlock mysqlSQB = (MySqlSelectQueryBlock) selectQuery;

				SQLTableSource tableSource = mysqlSQB.getFrom();

				if (tableSource instanceof SQLExprTableSource) {
					SQLExprTableSource exprTableSource = (SQLExprTableSource) tableSource;
					SQLExpr tableNameExpr = exprTableSource.getExpr();

					if (tableNameExpr instanceof SQLIdentifierExpr) {
						SQLIdentifierExpr tableNameIdentifier = (SQLIdentifierExpr) tableNameExpr;
						printTableName(tableNameIdentifier.getName(), tableNameIdentifier.getSimpleName(),
								tableNameIdentifier.getLowerName());
					} else if (tableNameExpr instanceof SQLPropertyExpr) {
						SQLPropertyExpr tableNamePropertyExpr = (SQLPropertyExpr) tableNameExpr;

						SQLIdentifierExpr owner = (SQLIdentifierExpr) tableNamePropertyExpr.getOwner();

						printTableName(tableNamePropertyExpr.getName(), tableNamePropertyExpr.getSimpleName(), null);
						printOutFile("DBName : " + owner.getName());
					}
				}
				printOutFile("tableName alias : " + tableSource.getAlias());

				SQLSelectGroupByClause sqlGroupBy = mysqlSQB.getGroupBy();
				if (sqlGroupBy instanceof SQLSelectGroupByClause) {
					List<SQLExpr> items = sqlGroupBy.getItems();
					printGroupFileds(items);
				}

				printSelectFileds(mysqlSQB.getSelectList());

				SQLOrderBy sqlOrderBy = mysqlSQB.getOrderBy();
				printOrderFileds(sqlOrderBy.getItems());

				SQLExprTableSource tableSource1 = mysqlSQB.getInto();
				if (null != tableSource1) {

				}

				List<SQLCommentHint> hints = mysqlSQB.getHints();
				if (hints.size() != 0) {

				}
			}
		}
	}

	public static void printOrderFileds(List<SQLSelectOrderByItem> list) {
		StringBuffer sb = new StringBuffer("Select Order By fileds : ");
		for (int i = 0; i < list.size() - 1; i++) {
			SQLSelectOrderByItem item = list.get(i);
			bufferAddExpr(item.getExpr(), sb);
			sb.append(", ");
		}
		bufferAddExpr(list.get(list.size() - 1).getExpr(), sb);

		printOutFile(sb.toString());
	}

	public static void forEachMap(Map<String, Object> map) {

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}

	public static void printSelectFileds(List<SQLSelectItem> list) {
		StringBuffer sb = new StringBuffer("Select fileds : ");
		for (int i = 0; i < list.size() - 1; i++) {
			SQLExpr expr = list.get(i).getExpr();
			mergeSelectItem(expr, sb);
			sb.append(", ");
		}
		SQLExpr expr = list.get(list.size() - 1).getExpr();
		mergeSelectItem(expr, sb);

		printOutFile(sb.toString());
	}

	public static void mergeSelectItem(SQLExpr expr, StringBuffer sb) {

		if (expr instanceof SQLIdentifierExpr) {
			bufferAddExpr(expr, sb);
		} else if (expr instanceof SQLAggregateExpr) {
			SQLAggregateExpr expr1 = (SQLAggregateExpr) expr;
			String methodName = expr1.getMethodName();
			List<SQLExpr> arguments = expr1.getArguments();
			for (int j = 0; j < arguments.size(); j++) {
				SQLIdentifierExpr expr2 = (SQLIdentifierExpr) arguments.get(j);
				sb.append(methodName + "(" + expr2.getName() + ")");
			}
		}
	}

	public static void printGroupFileds(List<SQLExpr> list) {
		StringBuffer sb = new StringBuffer("Group By fileds : ");

		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i) instanceof SQLIdentifierExpr) {
				bufferAddExpr(list.get(i), sb);
				sb.append(", ");
			}
		}
		bufferAddExpr(list.get(list.size() - 1), sb);

		printOutFile(sb.toString());
	}

	public static void bufferAddExpr(SQLExpr expr, StringBuffer sb) {
		SQLIdentifierExpr IdentiExpr = (SQLIdentifierExpr) expr;
		sb.append(IdentiExpr.getName());
	}

	public static void printTableName(String tableName, String simpleName, String lowerName) {
		StringBuffer sb = new StringBuffer("tableName : ");
		if (!StringUtils.isEmpty(tableName)) {
			sb.append(tableName);
		}
		if (!StringUtils.isEmpty(simpleName)) {
			sb.append(" , ").append("simpleName : ").append(simpleName);
		}
		if (!StringUtils.isEmpty(lowerName)) {
			sb.append(" , ").append("lowerName : ").append(lowerName);
		}
		sb.append(".");
		printCutOffRule();
		printOutFile(sb.toString());
	}

	public static void printCutOffRule() {
		System.out.println("================================================");
	}

	public static void printOutFile(String outString) {
		System.out.println(outString);
		printCutOffRule();
	}

	public static void main(String[] args) {
		analyzeSQL(SQL);
	}
}