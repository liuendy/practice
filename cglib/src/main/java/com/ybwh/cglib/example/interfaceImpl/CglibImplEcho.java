package com.ybwh.cglib.example.interfaceImpl;

import java.lang.reflect.Method;


import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

public class CglibImplEcho {

	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setInterfaces(new Class[] { Echo.class });

		enhancer.setCallbacks(new Callback[] { new MethodInterceptor() {

			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

				// 这里不调用 proxy.invokeSuper(obj, args);直接写方法的代码，相当于实现接口
				System.out.println("************" + args[0]);
				return null;
			}

		}, NoOp.INSTANCE });
		enhancer.setCallbackFilter(new CallbackFilter() {
			@Override
			public int accept(Method method) {
				// 只拦截方法名为add且只有 一个int型参数的方法
				if ("replay".equals(method.getName())) {
					return 0;
				}

				return 1;// 返回Callback数组下标
			}
		});

		
		Echo echo = (Echo) enhancer.create();
		
		
		Class<?> clazz = enhancer.createClass();
		
		System.out.println(clazz.getInterfaces());
		

		echo.replay("dddddddddddddddddd");
//		echo.replay2("eeeeee");

	}

}
