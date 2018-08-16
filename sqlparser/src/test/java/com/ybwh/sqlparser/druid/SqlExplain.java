package com.ybwh.sqlparser.druid;

import java.util.List;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;

public class SqlExplain {
 
    public static void main(String[] args){
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