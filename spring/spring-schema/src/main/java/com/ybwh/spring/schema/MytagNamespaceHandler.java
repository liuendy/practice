package com.ybwh.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.ybwh.spring.schema.parser.CompanyTagBeanDefinitionParser;
import com.ybwh.spring.schema.parser.DepartmentTagBeanDefinitionParser;
import com.ybwh.spring.schema.parser.EmployeeTagBeanDefinitionParser;
import com.ybwh.spring.schema.parser.MybeanTagBeanDefinitionParser;

/**
 * mytag命名空间句柄
 * 
 * @author fanbeibei
 *
 */
public class MytagNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
//		System.out.println("MytagNamespaceHandler");
		registerBeanDefinitionParser("employee", new EmployeeTagBeanDefinitionParser());
		registerBeanDefinitionParser("department", new DepartmentTagBeanDefinitionParser());
		registerBeanDefinitionParser("company", new CompanyTagBeanDefinitionParser());
		registerBeanDefinitionParser("mybean", new MybeanTagBeanDefinitionParser());
	}

}
