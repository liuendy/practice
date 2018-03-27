package com.fbb.codegen.main;

import java.sql.Connection;
import java.sql.DriverManager;

import com.fbb.codegen.core.CodeGen;
import com.fbb.codegen.mysql.MysqlCodeGen;

public class GenCodeStart {
	
	
	public static Connection getConnections(String driver, String url, String user, String pwd) {
		try {
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, user, pwd);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		final String url = "jdbc:mysql://localhost:3306/iov?characterEncoding=utf-8&useUnicode=true&zeroDateTimeBehavior=convertToNull";
		final String driver = "com.mysql.jdbc.Driver";
		final String user = "root";
		final String pwd = "fan1988";
		final String fileOutPath = "D:/tmp";
		final String packageName = "com.fbb.book.model";
		
		try {
			Connection conn = getConnections(driver, url, user, pwd);
			CodeGen codeGen = new MysqlCodeGen(conn);
			codeGen.createAllTableEntityFile(fileOutPath,packageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
