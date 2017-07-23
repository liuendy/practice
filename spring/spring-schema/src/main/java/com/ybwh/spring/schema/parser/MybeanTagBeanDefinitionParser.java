package com.ybwh.spring.schema.parser;

import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * 模仿bean和property标签 //TODO
 * 
 * @author fanbeibei
 *
 */
public class MybeanTagBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {

	/**
	 * 子节点的名称
	 */
	private static final String PROP = "prop";

	@Override
	protected Class<?> getBeanClass(Element element) {// 指定bean对应的Class对象
		try {
			return Class.forName(getBeanClassName(element));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected String getBeanClassName(Element element) {// 获取bean的Class名称
		return element.getAttribute("class");// 元素的class属性值
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		// 从标签中取出对应的属性值
		// String id = element.getAttribute("id");//id留给spring供其他bean引用
		 String clazz = element.getAttribute("class");
		 String scope = element.getAttribute("scope");

		
		
	}

	private void dealChildElementProp(Element propEle, ParserContext parserContext, BeanDefinitionBuilder builder) {
		String name = propEle.getAttribute("name");
		String value = propEle.getAttribute("vaule");
		if (null != value) {
			builder.addPropertyValue(name, value);
		} else {
			String ref = propEle.getAttribute("ref");
			builder.addPropertyReference(name, ref);
		}
	}

}
