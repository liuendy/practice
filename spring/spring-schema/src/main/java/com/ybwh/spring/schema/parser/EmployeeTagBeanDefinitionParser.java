package com.ybwh.spring.schema.parser;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.ybwh.spring.schema.model.Employee;

/**
 * <p>
 * employ标签解析器。最简单的自定义标签，仅含有属性没有子标签
 * 对应com.ybwh.spring.schema.model.Employee类
 * </p>
 * 
 * @author fanbeibei
 *
 */
public class EmployeeTagBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {
	private static final String NUM_ATTRIBUTE = "num";
	private static final String NAME_ATTRIBUTE = "name";
	
	
	@Override
	protected Class<?> getBeanClass(Element element) {// 指定bean对应的Class对象
		//这里可以扩展用自定义的加载器对加密的class文件进行加载，返回Class
		
		//可以确定bean类型所以直接指定
		return Employee.class;
	}

	
	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		// 从标签中取出对应的属性值
		// String id = element.getAttribute("id");//id留给spring供其他bean引用
		String numVal = element.getAttribute(NUM_ATTRIBUTE);
		String nameVal = element.getAttribute(NAME_ATTRIBUTE);
		
		if (!StringUtils.isEmpty(numVal)) {
			builder.addPropertyValue("num", numVal);
		}
		
		if (!StringUtils.isEmpty(nameVal)) {
			builder.addPropertyValue("name", nameVal);
		}
	}

}
