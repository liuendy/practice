package com.ybwh.hbase.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * hbase 列属性
 * 
 * @author Fanbeibei
 * @date 2017年5月17日 下午6:04:24
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface HbaseColumn {

	/**
	 * 列族
	 * 
	 * @return
	 */
	String family();

	/**
	 * 列名
	 * 
	 * @return
	 */
	String qualifier();

}
