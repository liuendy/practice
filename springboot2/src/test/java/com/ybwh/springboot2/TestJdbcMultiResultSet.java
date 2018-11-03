package com.ybwh.springboot2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class TestJdbcMultiResultSet {
	@Test
	public void test01() throws SQLException, ClassNotFoundException {//返回多个结果集的例子
	    String DBDRIVER = "com.mysql.jdbc.Driver";
	    String DBURL = "jdbc:mysql://localhost:3306/member?allowMultiQueries=true";
	    String DBUSER = "root";
	    String DBPASS = "root";

	    Connection conn = null; 
	    Class.forName(DBDRIVER); 
	    conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS); 
	    System.out.println(conn);
	    String sql = "SELECT * FROM category;"
	            +"SELECT * FROM user;" ;
	    Statement stmt = conn.createStatement();

	    boolean isResultSet = stmt.execute(sql);
	    ResultSet rs = null;
	    int count = 0;
	    while(true) {
	        if(isResultSet) {
	            rs = stmt.getResultSet();
	            while(rs.next()) {
	                System.out.println(rs.getString(1));
	            }
	            rs.close();
	        } else {
	            if(stmt.getUpdateCount() == -1) {
	                break;
	            }
	            System.out.printf("Result {} is just a count: {}", count, stmt.getUpdateCount());
	        }

	        count ++;
	        isResultSet = stmt.getMoreResults();
	    }
	    stmt.close();
	    conn.close(); 
	}

}
