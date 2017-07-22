package com.ybwh.spring.schema;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 
 * 
 * @author fanbeibei
 *
 */
public class MybeanTagBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser  {

	
	@Override
	protected Class<?> getBeanClass(Element element) {//指定bean对应的Class对象
		try {
			return Class.forName(getBeanClassName(element));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	protected String getBeanClassName(Element element) {//获取bean的Class名称
		return element.getAttribute("class");//元素的class属性值
	}
	
	
	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		 // 从标签中取出对应的属性值  
        String id = element.getAttribute("id");
        String clazz = element.getAttribute("class");
        String scope = element.getAttribute("scope");
        
        //
        
         
//        builder.addPropertyReference(name, beanName)
//        builder.addPropertyValue(name, value)
//        builder.addConstructorArgValue(value)
//        builder.addConstructorArgReference(beanName)
        
        
//        builder.rootBeanDefinition(beanClass)
        
        
//        parserContext.getRegistry().
        
        
        
        
        
        
         
         
         
	}

}
