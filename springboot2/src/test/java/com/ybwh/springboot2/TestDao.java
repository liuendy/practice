package com.ybwh.springboot2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot2.demo.dao.AreaDao;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDao {
	@Autowired
	AreaDao dao;

	@Test
	public void contextLoads() {
		try {
			Assert.assertNotNull(dao);
			
			System.out.println(dao.selectTest(110106));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
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
