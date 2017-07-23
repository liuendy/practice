package com.ybwh.spring.schema.parser;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.ybwh.spring.schema.model.Company;

/**
 * 
 * 同时含有属性和一个子标签的类
 * 对应com.ybwh.spring.schema.model.Department类
 * 
 * @author fanbeibei
 *
 */
public class CompanyTagBeanDefinitionParser2 extends AbstractSimpleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {// 指定bean对应的Class对象
		return Company.class;
	}


	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		// 从标签中取出对应的属性值
		String businessLicense = element.getAttribute("businessLicense");
		String name = element.getAttribute("name");
		
		if (!StringUtils.isEmpty(businessLicense)) {
			builder.addPropertyValue("businessLicense", Integer.parseInt(businessLicense));
		}
		
		if (!StringUtils.isEmpty(name)) {
			builder.addPropertyValue("name", name);
		}
		
		//子标签ceo属性的处理,当然你还可以以这种方式在这里处理其他一对一的子标签
		builder.addPropertyValue("ceo", parserContext.getDelegate()  
                .parseCustomElement(  
                        DomUtils.getChildElementByTagName(element, "employee"),  
                        builder.getRawBeanDefinition())); 
		
		//子标签depts的处理，当然你还可以以这种方式在这里处理其他一对多的子标签
		List<Element> deptEles = DomUtils.getChildElementsByTagName(element, "department");  
		ManagedList<BeanDefinition> list = new ManagedList<>();
		list.setMergeEnabled(true);
		list.setSource(parserContext.getReaderContext().extractSource(element));
		for (Element e:deptEles) {
			list.add(parserContext.getDelegate().parseCustomElement(  
                    e, builder.getRawBeanDefinition()));
		}
		
		builder.addPropertyValue("depts", list);
 		
	}

}
