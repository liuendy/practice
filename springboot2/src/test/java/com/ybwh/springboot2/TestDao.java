package com.ybwh.springboot2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ybwh.springboot2.common.mybatis.plugin.pagination.Pagination;
import com.ybwh.springboot2.demo.dao.AreaDao;
import com.ybwh.springboot2.demo.entity.Area;


@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties= {"spring.config.location=E:/application.yml"})
public class TestDao {
	Logger logger = LoggerFactory.getLogger("TTTTTTTTTTTTTTT");
	@Autowired
	AreaDao dao;

	@Test
	public void contextLoads() {
		logger.info("**************************************************");
		
		try {
			Assert.assertNotNull(dao);
			
			List<Area> list = dao.selectPagination(new HashMap<>(),new RowBounds(10, 10));
			
			System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
//	@Test
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
