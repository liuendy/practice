package com.ybwh.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * 修改字节码方式实现动态代理
 *
 */
public class ProxyByRepairClass {
	
	
	public static void main(String[] args) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
		 ClassPool pool = ClassPool.getDefault();

	        // 定义类
	        CtClass ctClass = pool.get("com.ybwh.javassist.Calculator");

	        // 需要修改的方法名称
	        String mname = "getSum";
	        CtMethod mold = ctClass.getDeclaredMethod(mname);
	        // 修改原有的方法名称
	        String nname = mname + "$impl";
	        mold.setName(nname);

	        //创建新的方法，复制原来的方法
	        CtMethod mnew = CtNewMethod.copy(mold, mname, ctClass, null);
	        // 主要的注入代码
	        StringBuffer body = new StringBuffer();
	        body.append("{\nlong start = System.currentTimeMillis();\n");
	        // 调用原有代码，类似于method();($$)表示所有的参数
			body.append(nname + "($$);\n");
	        body.append("System.out.println(\"Call to method " + mname
	                + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");

	        body.append("}");
	        // 替换新方法
	        mnew.setBody(body.toString());
	        // 增加新方法
	        ctClass.addMethod(mnew);

	        Calculator calculator =(Calculator)ctClass.toClass().newInstance(); 

	        calculator.getSum(10000);
	}

}


class Calculator {

    public void getSum(long n) {
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        System.out.println("n="+n+",sum="+sum);
    }
}