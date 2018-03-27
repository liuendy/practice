package com.fbb.codegen.main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.fbb.codegen.mysql.MysqlTypeConverter;
import com.fbb.codegen.util.CodeGenUtil;

public class DBHelpInfo {

	private static final String url = "jdbc:mysql://localhost:3306/yjs?characterEncoding=utf-8&useUnicode=true&zeroDateTimeBehavior=convertToNull";
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String user = "root";
	private static final String pwd = "fan1988";

	public static void main(String[] args) {
//		FileSystemView fsv = FileSystemView.getFileSystemView();
//		String path = fsv.getHomeDirectory().toString();// 获取当前用户桌面路径
		Connection connection = getConnections(driver, url, user, pwd);
		try {
			DatabaseMetaData dbmd = connection.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				// System.out.println(tableName);
				// ResultSet rs
				// =getConnection.getMetaData().getColumns(null,
				// getXMLConfig.getSchema(),tableName.toUpperCase(),
				// "%");//其他数据库不需要这个方法的，直接传null，这个是oracle和db2这么用
				ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
				
				System.out.println("表名：" + tableName +",主键"+dbmd.getPrimaryKeys(null, null, tableName) + "\t\n表字段信息：");
				while (rs.next()) {
					
					System.out.println("字段名：" + rs.getString("COLUMN_NAME") + "\t字段注释：" + rs.getString("REMARKS")
							+ "\t字段数据类型：" + rs.getString("TYPE_NAME").toLowerCase());
					
					System.out.println(MysqlTypeConverter.columnType2JavaType(rs.getString("TYPE_NAME"))+"  "+CodeGenUtil.columnName2PropName(rs.getString("COLUMN_NAME")));
				}
				
				System.out.println("生成成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnections(String driver, String url, String user, String pwd) {
		try {
			// Properties props =new Properties();
			// props.put("remarksReporting","true");
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, user, pwd);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 其他数据库不需要这个方法 oracle和db2需要
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getSchema(Connection connection) throws Exception {
		String schema;
		schema = connection.getMetaData().getUserName();
		if ((schema == null) || (schema.length() == 0)) {
			throw new Exception("ORACLE数据库模式不允许为空");
		}
		return schema.toUpperCase().toString();

	}

}