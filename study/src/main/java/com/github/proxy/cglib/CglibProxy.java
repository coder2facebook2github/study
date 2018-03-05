package com.github.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {


	public <T>  T getInstance(Class<T> tClass) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(tClass);
		enhancer.setCallback(this);
		return (T)enhancer.create();
	}
	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("before --------------");
		Object result = methodProxy.invokeSuper(o, objects);

		System.out.println("end -------------------");
		return result;
	}
}
