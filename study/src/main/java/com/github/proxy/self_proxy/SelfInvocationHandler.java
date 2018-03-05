package com.github.proxy.self_proxy;

import java.lang.reflect.Method;

public interface SelfInvocationHandler {
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable;

}
