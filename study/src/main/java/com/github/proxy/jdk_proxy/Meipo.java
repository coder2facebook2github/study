package com.github.proxy.jdk_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Meipo implements InvocationHandler{

	private Person target;

	//获取被代理人的资料
	public Object getInstance(Person target)throws Exception {
		this.target = target;
		Class clazz = target.getClass();
		System.out.println("被代理对象的class是: " + clazz);
		return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("我是媒婆，你的性别是: " + this.target.getSex());
		System.out.println("开始进行海选...");
		System.out.println("-------------");
//		this.target.findLove();
		method.invoke(this.target, args);
		System.out.println("-------------");
		System.out.println("如果合适的话，");
		return null;
	}
}
