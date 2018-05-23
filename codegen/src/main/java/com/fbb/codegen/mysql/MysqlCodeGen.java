package com.fbb.codegen.mysql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.fbb.codegen.core.CodeGen;
import com.fbb.codegen.core.model.EntityCodeInfo;
import com.fbb.codegen.core.model.PropVarInfo;
import com.fbb.codegen.util.CodeGenUtil;

/**
 * mysql代码生成器
 * 
 * @author fanbeibei
 *
 */
public class MysqlCodeGen extends CodeGen {

	private Logger logger = Logger.getLogger(MysqlCodeGen.class);

	/**
	 * 数据库连接
	 */
	private Connection conn;

	public MysqlCodeGen(Connection conn) {
		if (null == conn) {
			throw new IllegalArgumentException("JDBC Connection can not be null!!!");
		}
		this.conn = conn;
	}

	@Override
	public void createAllTableEntityFile(final String fileOutPath, final String packageName) {
		try {
			File fileOutPathFile = new File(fileOutPath);

			if (!fileOutPathFile.exists()) {// 不存在则创建目录
				fileOutPathFile.mkdirs();
			}

			if (!fileOutPathFile.isDirectory()) {
				throw new IOException(fileOutPath + " is not a directory path");
			}

			List<EntityCodeInfo> eciList = getAllEntityCodeInfo(packageName);
			for (EntityCodeInfo eci : eciList) {
				createEntityFile(fileOutPath, eci);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成文件失败");
		}
	}

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 创建实体文件
	 * 
	 * @param fileOutPath
	 * @param eci
	 * @throws IOException
	 */
	private void createEntityFile(String fileOutPath, EntityCodeInfo eci) throws IOException {

		StringBuilder entityCode = new StringBuilder();
		entityCode.append(createPackageDeclaration(eci.getPackageDeeclaration()));// 包声明

		if (null != eci.getImportList()) {
			for (String className : eci.getImportList()) {
				entityCode.append(createImportDeclaration(className));
			}
		}

		entityCode.append(MUST_IMPORT);

		entityCode.append(createClassDeclaration(eci.getTableName(), eci.getIdColumnName(), eci.getClassName()));

		StringBuilder getterAndSetter = new StringBuilder();

		for (PropVarInfo pvi : eci.getVarsList()) {
			if (pvi.isId()) {
				entityCode.append(createIdVarDeclaration(pvi.getComment(), eci.getTableName(), pvi.getColumnName(),
						pvi.getType(), pvi.getVarName()));
			} else {
				entityCode.append(
						createVarDeclaration(pvi.getComment(), pvi.getColumnName(), pvi.getType(), pvi.getVarName()));
			}

			getterAndSetter.append(createGetter(pvi.getComment(), pvi.getVarName(), pvi.getType()))
					.append(createSetter(pvi.getComment(), pvi.getVarName(), pvi.getType()));

		}

		entityCode.append(getterAndSetter).append(END);

		logger.debug(entityCode);

		FileWriter fw = new FileWriter(fileOutPath + File.separator + eci.getClassName() + ".java");
		PrintWriter pw = new PrintWriter(fw);

		pw.write(entityCode.toString());
		pw.flush();
		pw.close();
	}

	private List<EntityCodeInfo> getAllEntityCodeInfo(final String packageName) throws Exception {

		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
		List<EntityCodeInfo> eciList = new ArrayList<EntityCodeInfo>();

		while (resultSet.next()) {
			// 获取表名
			String tableName = resultSet.getString("TABLE_NAME");

			// System.out.println(resultSet.getString("REMARKS"));
			// System.out.println("*******************************");

			logger.info("表名：" + tableName);

			Set<String> pkNameSet = getPkName(dbmd, tableName);
			logger.info("主键名：" + pkNameSet);

			EntityCodeInfo eci = new EntityCodeInfo();

			String clazzName = CodeGenUtil.tableName2ClassName(tableName);
			logger.info("类名：" + clazzName);
			eci.setClassName(clazzName);
			eci.setTableName(tableName);
			// 设置包名
			eci.setPackageDeeclaration(packageName);

			SortedSet<String> importList = new TreeSet<String>();
			List<PropVarInfo> varsList = new ArrayList<PropVarInfo>(15);

			ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");

			while (rs.next()) {
				PropVarInfo pvi = new PropVarInfo();

				String columJavaType = MysqlTypeConverter.columnType2JavaType(rs.getString("TYPE_NAME"));// 列对应的Java类型
				String columName = rs.getString("COLUMN_NAME");// 列名

				if (pkNameSet.contains(columName)) {
					pvi.setIsId(true);
					eci.setIdColumnName(columName);
				} else {
					pvi.setIsId(false);
				}

				pvi.setType(columJavaType.substring(columJavaType.lastIndexOf(".") + 1));

				if (!columJavaType.startsWith("java.lang.")) {
					importList.add(columJavaType);
				}

				pvi.setColumnName(columName);
				pvi.setComment(rs.getString("REMARKS"));

				String varName = CodeGenUtil.columnName2PropName(columName);
				pvi.setVarName(varName);

				logger.info("列名:" + columName + " -> 属性名：" + varName);

				varsList.add(pvi);

				logger.info("字段名：" + rs.getString("COLUMN_NAME") + "\t字段注释：" + rs.getString("REMARKS") + "\t字段数据类型："
						+ rs.getString("TYPE_NAME").toLowerCase());
			}

			eci.setImportList(importList);
			eci.setVarsList(varsList);
			eciList.add(eci);

			logger.info("-------------------------------------------" + clazzName
					+ "生成成功！-----------------------------------------------");
		}

		if (0 == eciList.size()) {
			return null;
		}

		return eciList;

	}

	/**
	 * 
	 * 获取 主键列表
	 * 
	 * @param dbmd
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private Set<String> getPkName(DatabaseMetaData dbmd, String tableName) throws SQLException {

		ResultSet pkRSet = dbmd.getPrimaryKeys(null, null, tableName);
		Set<String> pkNameSet = new HashSet<String>();
		while (pkRSet.next()) {
			// System.out.println("TABLE_CAT : "+pkRSet.getObject(1));
			// System.out.println("TABLE_SCHEM: "+pkRSet.getObject(2));
			// System.out.println("TABLE_NAME : "+pkRSet.getObject(3));
			// System.out.println("COLUMN_NAME: "+pkRSet.getObject(4));
			// System.out.println("KEY_SEQ : "+pkRSet.getObject(5));
			// System.out.println("PK_NAME : "+pkRSet.getObject(6));
			pkNameSet.add(pkRSet.getString(4));
		}

		if (0 == pkNameSet.size()) {
			return null;
		}

		return pkNameSet;
	}

}
