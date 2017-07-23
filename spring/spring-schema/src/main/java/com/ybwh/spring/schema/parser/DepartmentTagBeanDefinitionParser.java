package com.ybwh.spring.schema.parser;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.ybwh.spring.schema.model.Department;

/**
 * 
 * 同时含有属性和一个子标签的类
 * 对应com.ybwh.spring.schema.model.Department类
 * 
 * @author fanbeibei
 *
 */
public class DepartmentTagBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {

	private static final String EMPLOYEECOUNT_ATTRIBUTE = "employeeCount";
	private static final String NAME_ATTRIBUTE = "name";
	private static final String EMPLOYEE_ELEMENT = "employee";
	
	
	@Override
	protected Class<?> getBeanClass(Element element) {// 指定bean对应的Class对象
		return Department.class;
	}


	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		// 从标签中取出对应的属性值
		String employeeCount = element.getAttribute(EMPLOYEECOUNT_ATTRIBUTE);
		String name = element.getAttribute(NAME_ATTRIBUTE);
		
		if (!StringUtils.isEmpty(employeeCount)) {
			builder.addPropertyValue(EMPLOYEECOUNT_ATTRIBUTE, Integer.parseInt(employeeCount));
		}
		
		if (!StringUtils.isEmpty(name)) {
			builder.addPropertyValue(NAME_ATTRIBUTE, name);
		}
		
		//子标签head属性的处理,当然你还可以以这种方式在这里处理其他一对一的子标签
		builder.addPropertyValue("head", parserContext.getDelegate()  
                .parseCustomElement(  
                        DomUtils.getChildElementByTagName(element, EMPLOYEE_ELEMENT),  
                        builder.getRawBeanDefinition())); 
		
		
 		
	}

}
