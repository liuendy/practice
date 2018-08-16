package com.ybwh.sqlparser.druid;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;

/**
 *
 *
 * @author beanlam
 * @date 2017年1月10日 下午11:06:26
 * @version 1.0
 *
 */
public class ParserMain {

    public static void main(String[] args) {
        String sql = "select id,name from user";

        // 新建 MySQL Parser
        SQLStatementParser parser = new MySqlStatementParser(sql);

        // 使用Parser解析生成AST，这里SQLStatement就是AST
        SQLStatement statement = parser.parseStatement();

        // 使用visitor来访问AST
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);
                
        // 从visitor中拿出你所关注的信息        
        System.out.println(visitor.getColumns());
    }
}