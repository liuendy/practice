package com.fbb.codegen.core.model;

import java.util.List;
import java.util.SortedSet;

/**
 * 实体类代码信息
 * 
 * @author fanbeibei
 *
 */
public class EntityCodeInfo {
	/**
	 * 包声明
	 */
	private String packageDeeclaration;

	/**
	 * 导入声明
	 */
	private SortedSet<String> importList;

	/**
	 * 类名
	 */
	private String className;
	
	
	/**
	 * 表名称
	 */
	private String tableName;
	
	/**
	 * id列名
	 */
	private String idColumnName;

	/**
	 * 变量信息列表 
	 */
	private List<PropVarInfo> varsList;

	/**
	 * 获取 包声明
	 * 
	 * @return packageDeeclaration
	 */
	public String getPackageDeeclaration() {
		return packageDeeclaration;
	}

	/**
	 * 设置 包声明
	 * 
	 * @param packageDeeclaration
	 *            包声明
	 */
	public void setPackageDeeclaration(String packageDeeclaration) {
		this.packageDeeclaration = packageDeeclaration;
	}

	/**
	 * 获取 类名
	 * 
	 * @return className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * 设置 类名
	 * 
	 * @param className
	 *            类名
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 获取 导入声明
	 * 
	 * @return importList
	 */
	public SortedSet<String> getImportList() {
		return importList;
	}

	/**
	 * 设置 导入声明
	 * 
	 * @param importList
	 *            导入声明
	 */
	public void setImportList(SortedSet<String> importList) {
		this.importList = importList;
	}

	/** 
	 * 获取 变量信息列表 
	 * @return varsList 
	 */
	public List<PropVarInfo> getVarsList() {
		return varsList;
	}
	

	/** 
	 * 设置 变量信息列表 
	 * @param varsList 变量信息列表 
	 */
	public void setVarsList(List<PropVarInfo> varsList) {
		this.varsList = varsList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EntityCodeInfo [packageDeeclaration=" + packageDeeclaration + ", importList=" + importList
				+ ", className=" + className + ", varsList=" + varsList + "]";
	}

	/** 
	 * 获取 表名称 
	 * @return tableName 
	 */
	public String getTableName() {
		return tableName;
	}
	

	/** 
	 * 设置 表名称 
	 * @param tableName 表名称 
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/** 
	 * 获取 id列名 
	 * @return idColumnName 
	 */
	public String getIdColumnName() {
		return idColumnName;
	}
	

	/** 
	 * 设置 id列名 
	 * @param idColumnName id列名 
	 */
	public void setIdColumnName(String idColumnName) {
		this.idColumnName = idColumnName;
	}
	
	
	


}
