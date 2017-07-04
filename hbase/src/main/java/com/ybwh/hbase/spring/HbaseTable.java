package com.ybwh.hbase.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* 对象对应的表
* @author Fanbeibei
* @date 2017年5月17日 下午6:01:46 
*  
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface HbaseTable {
	/** 
	* 命名空间
	* @return 
	*/
	String namespace() default "default";
	/** 
	* 表名
	* @return 
	*/
	String name();
}
