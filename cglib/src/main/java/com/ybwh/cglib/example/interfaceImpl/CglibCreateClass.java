package com.ybwh.cglib.example.interfaceImpl;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibCreateClass {

	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setInterfaces(new Class[] { Echo.class });
		
		/*enhancer.setCallback(new MethodInterceptor() {
			
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}
		});
*/
		Class<?> clazz = enhancer.createClass();
		
		System.out.println(clazz.getTypeName());

	}

}
