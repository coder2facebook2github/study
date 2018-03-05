package com.github.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Meipo implements MethodInterceptor {
	public <T> T getInstance(Class<T> clazz) throws Exception{
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return (T)enhancer.create();
	}


	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

		System.out.println("start ------------");
		Object result = methodProxy.invokeSuper(o, objects);

		System.out.println("end ---------------");
		return result;
	}
}
