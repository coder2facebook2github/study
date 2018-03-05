package com.github.proxy.self_proxy;

import java.lang.reflect.Method;

public class SelfMeipo implements SelfInvocationHandler {

	private Person target;

	public Object getInstance(Person target){
			this.target = target;
		Class clazz = target.getClass();
		System.out.println("被代理对象的class是: " + clazz);
		return SelfProxy.newProxyInstance(new SelfClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("我是媒婆");
		System.out.println("开始进行海选...");
		System.out.println("-------------");
//		this.target.findLove();
		Object result = method.invoke(this.target, args);
		System.out.println("-------------");
		System.out.println("如果合适的话，");
		return result;
	}
}
