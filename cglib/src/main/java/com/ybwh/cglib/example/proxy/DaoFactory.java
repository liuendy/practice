package com.ybwh.cglib.example.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class DaoFactory {

	public static Dao create() {

		Enhancer enhancer = new Enhancer();

		enhancer.setSuperclass(DaoImpl.class);

		enhancer.setCallbacks(new Callback[] { new Proxy(), NoOp.INSTANCE });
		enhancer.setCallbackFilter(new CallbackFilter() {

			@Override
			public int accept(Method method) {
				// 只拦截方法名为add且只有 一个int型参数的方法
				if ("add".equals(method.getName()) && 1 == method.getParameterTypes().length
						&& int.class.equals(method.getParameterTypes()[0])) {
					return 0;
				}

				return 1;// 返回Callback数组下标
			}
		});

		Dao dao = (Dao) enhancer.create();

		return dao;

	}

}