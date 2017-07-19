package com.ybwh.cglib.example.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class Proxy implements MethodInterceptor {

    @Override

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.out.println("拦截前...");

 

        // 输出参数类型

        for (Object arg : args) {

            System.out.print(arg.getClass() + ";");

        }
        
        
        Object result = proxy.invokeSuper(obj, args);//这里也可以不执行直接返回null

 

        System.out.println("拦截后...");

        return result;

    }

}