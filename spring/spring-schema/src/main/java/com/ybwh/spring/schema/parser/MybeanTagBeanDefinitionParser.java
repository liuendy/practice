package com.ybwh.spring.schema.parser;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

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
	private static final String PROPERTY = "property";

	private Class<?> beanClazz;

	@Override
	protected Class<?> getBeanClass(Element element) {// 指定bean对应的Class对象
		try {
			beanClazz = Class.forName(getBeanClassName(element));
			return beanClazz;
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

		List<Element> els = DomUtils.getChildElements(element);
		// 解析属性
		/*
		 * NamedNodeMap nnm = element.getAttributes(); for (int i = 0; i
		 * <nnm.getLength(); i++) { Node node = nnm.item(i); String key =
		 * node.getLocalName(); String value = node.getNodeValue(); if
		 * ("id".equals(key)) { continue; } if ("entity".equals(key)){
		 * if(parserContext.getRegistry().containsBeanDefinition(value)) {
		 * builder.addPropertyValue(key,parserContext.getRegistry().
		 * getBeanDefinition(value)); }else{
		 * builder.addPropertyReference(key,value); } }else{
		 * builder.addPropertyValue(key,value); } }
		 */
		for (Element element2 : els) {
			String name = element2.getAttribute("name");
			String value = element2.getAttribute("value");
			String ref = element2.getAttribute("ref");
			if (!StringUtils.hasText(ref)) {

				try {
					Field field = beanClazz.getDeclaredField("name");
					if (int.class.equals(field.getType()) || Integer.class.equals(field.getType())) {
						builder.addPropertyValue(name, Integer.parseInt(value));
					}

					if (String.class.equals(field.getType())) {
						builder.addPropertyValue(name, value);
					}

				} catch (NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}

			} else {
				if (parserContext.getRegistry().containsBeanDefinition(ref)) {
					builder.addPropertyValue(name, parserContext.getRegistry().getBeanDefinition(ref));
				} else {
					builder.addPropertyReference(name, ref);
				}
			}
		}

	}

}
