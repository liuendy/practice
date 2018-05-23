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
		final String url = "jdbc:mysql://172.16.101.130:3306/ehr_message?characterEncoding=utf-8&useUnicode=true&zeroDateTimeBehavior=convertToNull";
		final String driver = "com.mysql.jdbc.Driver";
		final String user = "eHRAdmin";
		final String pwd = "shangde_wings";
		final String fileOutPath = "E:/tmp";
		final String packageName = "com.fbb.book.model";
		
		try {
			System.out.println("start gen code .....");
			Connection conn = getConnections(driver, url, user, pwd);
			CodeGen codeGen = new MysqlCodeGen(conn);
			codeGen.createAllTableEntityFile(fileOutPath,packageName);
			System.out.println("gen code  success!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
