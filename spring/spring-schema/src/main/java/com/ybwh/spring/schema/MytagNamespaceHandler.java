package com.ybwh.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * mytag命名空间句柄
 * 
 * @author fanbeibei
 *
 */
public class MytagNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		////配置<mytag:mybean>标签解析器
		registerBeanDefinitionParser("mybean", new MybeanTagBeanDefinitionParser());
	}

}
