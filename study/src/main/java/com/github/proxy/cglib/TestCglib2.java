package com.github.proxy.cglib;

import com.github.proxy.cglib.CglibProxy;
import com.github.proxy.cglib.Plus;
import com.github.proxy.jdk_proxy.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

public class TestCglib2 {

	public static void main(String... args) {
		CglibProxy cglibProxy = new CglibProxy();
		Plus plus = cglibProxy.getInstance(Plus.class);
		Class plusClass = plus.getClass();
		String className = plusClass.getSimpleName();
		System.out.println(className);
		try {
			byte[] data = ProxyGenerator.generateProxyClass(className, new Class[]{Plus.class});
			FileOutputStream outputStream = new FileOutputStream("D:\\360Downloads/"+className+".class");
			outputStream.write(data);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


		int value = plus.fun(1, 2);
		System.out.println(value);
	}
}
