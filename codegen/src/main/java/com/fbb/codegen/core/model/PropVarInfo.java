package com.fbb.codegen.core.model;

/**
 * 类成员变量信息
 * 
 * @author fanbeibei
 *
 */
public class PropVarInfo {

	/**
	 * 是否是Id
	 */
	private boolean isId = false;
	/**
	 * 变量名
	 */
	private String varName;
	/**
	 * 变量类型
	 */
	private String type;
	/**
	 * 注释
	 */
	private String comment;
	/**
	 * 列名
	 */
	private String columnName;

	/**
	 * 获取 是否是Id
	 * 
	 * @return isId
	 */
	public boolean isId() {
		return isId;
	}

	/**
	 * 设置 是否是Id
	 * 
	 * @param isId
	 *            是否是Id
	 */
	public void setIsId(boolean isId) {
		this.isId = isId;
	}

	/**
	 * 获取 变量名
	 * 
	 * @return varName
	 */
	public String getVarName() {
		return varName;
	}

	/**
	 * 设置 变量名
	 * 
	 * @param varName
	 *            变量名
	 */
	public void setVarName(String varName) {
		this.varName = varName;
	}

	/**
	 * 获取 变量类型
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 变量类型
	 * 
	 * @param type
	 *            变量类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 注释
	 * 
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 设置 注释
	 * 
	 * @param comment
	 *            注释
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 获取 列名
	 * 
	 * @return columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * 设置 列名
	 * 
	 * @param columnName
	 *            列名
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PropVarInfo [isId=" + isId + ", varName=" + varName + ", type=" + type + ", comment=" + comment
				+ ", columnName=" + columnName + "]";
	}

}
