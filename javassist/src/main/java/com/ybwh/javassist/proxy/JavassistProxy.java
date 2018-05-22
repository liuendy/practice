package com.ybwh.javassist.proxy;

/**
 * javassist提供的代理
 *
 */
public class JavassistProxy {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		System.out.println("*******************方式一*******************");
        JavassistProxyFactory<A> jpf = new JavassistProxyFactory<A>();
        A a = new A();
        jpf.setTarget(a);
        A proxy = jpf.getProxy();
        proxy.del();

        System.out.println("*******************方式二*******************");
        JavassistProxyFactory02 jpf02 = new JavassistProxyFactory02();
        A a2 = (A) jpf02.getProxy(A.class);
        a2.del();
        a2.save();

	}
}
