package com.ybwh.spring.schema;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ybwh.spring.schema.model.Company;
import com.ybwh.spring.schema.model.Department;
import com.ybwh.spring.schema.model.Employee;

public class TestSchema {
	@Test
	public void testSimpleCostomerTag(){//最简单的标签，还可以给其他bean引用
		
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:*.xml");  

			Employee hr = (Employee) ctx.getBean("hr");
			System.out.println("hr ->"+hr);
			
			Department personnalDept =  (Department) ctx.getBean("personnalDept");
			System.out.println(personnalDept);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testCostomerTagWithSubTagOneToOne(){//带子标签的自定义标签
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:*.xml");  

			Department personnalDept =  (Department) ctx.getBean("devDept");
			System.out.println(personnalDept);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testCostomerTagWithSubTagOneToMany(){//带子标签一对多关系的自定义标签
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:*.xml");  

			Company myCompany =  (Company) ctx.getBean("myCompany");
			System.out.println(myCompany);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
