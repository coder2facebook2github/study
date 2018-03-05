package com.github.proxy.jdk_proxy;


import java.io.FileOutputStream;

public class TestFindLove {


	public static void main(String... args) throws Exception {
//		new XiaoXingxing().findLove();
		Person person = (Person) new Meipo().getInstance(new XiaoXingxing());
		System.out.println("代理对象是: " + person.getClass());
		person.findLove();
		String className = person.getClass().getSimpleName();
		//原理
		/**
		 * 1. 拿到被代理对象的引用，然后获取他的接口
		 * 2. JDK代理重新生成一个类，同时实现我们给的代理对象所实现的接口
		 * 3. 把被代理对象的引用也拿到了
		 * 4. 重新动态生成一个class字节码
		 * 5. 然后编译
		 */

		byte[] data = ProxyGenerator.generateProxyClass(className, new Class[]{Person.class});
		FileOutputStream outputStream = new FileOutputStream("D:\\360Downloads/"+className+".class");
		outputStream.write(data);
		outputStream.close();


	}
}
