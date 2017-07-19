package com.ybwh.javassist;

import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.IntegerMemberValue;

/**
 * 测试使用javassist生成一个新的类
 */
public class Demo01 {
	public static void main(String[] args) throws CannotCompileException, Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass("com.test.bean.Emp");

		// 创建属性
		CtField f1 = CtField.make("private int empno;", cc);
		CtField f2 = CtField.make("private String ename;", cc);
		cc.addField(f1);
		cc.addField(f2);

		// 创建方法
		CtMethod m1 = CtMethod.make("public int getEmpno(){return empno;}", cc);
		CtMethod m2 = CtMethod.make("public void setEmpno(){this.empno = empno;}", cc);
		
		
		// 给方法加注解，不支持给类加注解
		ConstPool  constPool = cc.getClassFile().getConstPool();
        AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation annot = new Annotation("MyAnnotation", constPool);
        annot.addMemberValue("value", new IntegerMemberValue(cc.getClassFile().getConstPool(), 0));
        attr.addAnnotation(annot);
        m1.getMethodInfo().addAttribute(attr);
		
		cc.addMethod(m1);
		cc.addMethod(m2);
		

		// 添加构造器
		CtConstructor constructor = new CtConstructor(new CtClass[] { CtClass.intType, pool.get("java.lang.String") },
				cc);
		constructor.setBody("{this.empno = empno; this.ename = ename;}");
		cc.addConstructor(constructor);

		cc.writeFile("D:/work/proj/rooster/javassist");// 将上面构造好的类写入到指定的工作空间中

		// 通过反射查看所有方法名
		System.out.println("======================");
		Class<?> clazz = cc.toClass();
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i].getName());
		}

		System.out.println("生成类，成功！");
	}
}