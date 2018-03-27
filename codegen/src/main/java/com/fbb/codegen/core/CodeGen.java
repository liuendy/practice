package com.fbb.codegen.core;

import java.sql.Connection;

/**
 * 代码生成器
 * 
 * @author fanbeibei
 *
 */
public abstract class CodeGen {
	
	/**
	 * 包声明
	 */
	public static final String PACKAGE_DECLARATION = "package {{packageName}};\n\n";
	
	
	/**
	 * 导包声明
	 */
	public static final String IMPORT_DECLARATION = "import {{className}};\n";
	
	/**
	 * 必须有的import语句
	 */
	public static final String MUST_IMPORT = 
			"\nimport javax.persistence.Column;\n"
			+"import javax.persistence.Entity;\n"
			+"import javax.persistence.Id;\n"
			+"import javax.persistence.SequenceGenerator;\n"
			+"import javax.persistence.Table;\n"
			+"import javax.persistence.UniqueConstraint;\n\n";
	
	/**
	 * 类声明
	 */
	public static final String CLASS_DECLARATION = 
			"/**\n"
			+"*\n"
			+"*/\n"
			+"@Entity \n"
			+"@Table(name = \"{{tableName}}\", uniqueConstraints = { @UniqueConstraint(columnNames = { \"{{idName}}\" }) })\n"
			+"public class {{className}} {\n\n";
	

	/**
	 * id变量声明
	 */
	public static final String ID_VAR_DECLARATION = 
			"\t/**\n\t"
			+"*{{comment}}\n\t"
			+"*/\n\t"
			+"@Id\n\t"
			+"@SequenceGenerator(name = \"{{tableName}}\")\n\t"
			+"@Column(name = \"{{idName}}\")\n\tprivate {{type}} {{idPropName}};\n\t\n\t";

	/**
	 * 普通变量声明
	 */
	public static final String VAR_DECLARATION = 
			"/**\n\t"
			+"*{{comment}}\n\t"
			+"*/\n\t"
			+"@Column(name = \"{{columName}}\")\n\tprivate {{type}} {{varName}};\n\t\n\t";

	/**
	 * 普通变量声明
	 */
	public static final String SETTER = 
			"/** \n\t" 
			+ "* 获取 {{comment}} \n\t" 
			+ "* @return {{varName}} \n\t" 
			+ "*/\n\t"
			+ "public {{varType}} get{{VarName}}() {\n\t"
			+ "\treturn {{varName}};\n\t" 
			+ "}\n\n\t";

	/**
	 * 普通变量声明
	 */
	public static final String GETTER = 
			"/** \n\t"
			+"* 设置 {{varName}} \n\t" 
			+"* @param {{varName}} {{comment}} \n\t" 
			+"*/ \n\t"
			+"public void set{{VarName}}({{varType}} {{varName}}) {\n\t"
			+"\tthis.{{varName}} = {{varName}};\n\t"
			+"}\n\n\t";
	
	public static final String END ="\n}";
	
	
	/**
	 * 创建包声明
	 * @param packageName 包名
	 * @return
	 */
	public String createPackageDeclaration(String packageName){
		return PACKAGE_DECLARATION.replaceAll("\\{\\{packageName\\}\\}", packageName);
	}
	
	public String createImportDeclaration(String className){
		return IMPORT_DECLARATION.replaceAll("\\{\\{className\\}\\}", className);
	}
	
	
	/**
	 * 
	 * 创建类声明
	 * @param tableName 表名
	 * @param idName id 列名
	 * @param className 类名
	 * @return
	 */
	public String createClassDeclaration(String tableName, String idName, String className) {
		return CLASS_DECLARATION.replaceAll("\\{\\{tableName\\}\\}", tableName).replaceAll("\\{\\{idName\\}\\}", idName)
				.replaceAll("\\{\\{className\\}\\}", className);
	}
	
	
	/**
	 * 创建id变量声明
	 * @param comment 注释
	 * @param tableName 表名
	 * @param idName id列名
	 * @param type id类型
	 * @param idPropName id属性名
	 * @return
	 */
	public String createIdVarDeclaration(String comment, String tableName, String idName, String type,
			String idPropName) {
		return ID_VAR_DECLARATION.replaceAll("\\{\\{comment\\}\\}", comment).replaceAll("\\{\\{tableName\\}\\}", tableName)
				.replaceAll("\\{\\{idName\\}\\}", idName).replaceAll("\\{\\{type\\}\\}", type)
				.replaceAll("\\{\\{idPropName\\}\\}", idPropName);

	}
	
	/**
	 * 创建普通变量声明
	 * 
	 * @param comment 注释
	 * @param columName 列名
	 * @param type 类型
	 * @param varName 变量名
	 * @return
	 */
	public String createVarDeclaration(String comment, String columName, String type, String varName) {
		
		return VAR_DECLARATION.replaceAll("\\{\\{comment\\}\\}", comment).replaceAll("\\{\\{columName\\}\\}", columName)
				.replaceAll("\\{\\{type\\}\\}", type).replaceAll("\\{\\{varName\\}\\}", varName);
	}
	
	
	/**
	 * 创建setter方法
	 * @param comment 注释
	 * @param varName 变量名
	 * @param varType 变量类型
	 * @return
	 */
	public String createSetter(String comment, String varName, String varType) {
		String VarName = varName.substring(0,1).toUpperCase()+varName.substring(1);
		return SETTER.replaceAll("\\{\\{comment\\}\\}", comment).replaceAll("\\{\\{varName\\}\\}", varName)
				.replaceAll("\\{\\{varType\\}\\}", varType).replaceAll("\\{\\{VarName\\}\\}", VarName);
	}
	
	
	/**
	 * 创建getter方法
	 * @param comment 注释
	 * @param varName 变量名
	 * @param varType 变量类型
	 * @return
	 */
	public String createGetter(String comment, String varName, String varType) {
		String VarName = varName.substring(0,1).toUpperCase()+varName.substring(1);
		return GETTER.replaceAll("\\{\\{comment\\}\\}", comment).replaceAll("\\{\\{varName\\}\\}", varName)
				.replaceAll("\\{\\{varType\\}\\}", varType).replaceAll("\\{\\{VarName\\}\\}", VarName);
	}
	
	/**
	 * 创建所有的实体文件
	 * @param fileOutPath 
	 * @param packageName
	 */
	public abstract void createAllTableEntityFile( String fileOutPath,String packageName);
	
	/** 
	 * 设置 数据库连接 
	 * @param conn 数据库连接 
	 */
	public abstract void setConn(Connection conn);
}
