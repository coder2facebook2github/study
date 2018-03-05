package com.github.proxy.cglib;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

abstract class BaseGen {
	protected List<String> calledMethods = new LinkedList<>(); // property

	public int getRandomInt() { // Get a random integer
		return new Random().nextInt(10000);
	}

	public abstract void printClassInfo(); // abstract method to print class info
}

class DynamicGen implements MethodInterceptor {

	public static <T> T newInstance(Class<? extends BaseGen> clazz) {
		Enhancer e = new Enhancer();
		e.setSuperclass(clazz);
		e.setCallback(new DynamicGen());
		return (T) e.create(); // Generate a class object
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
		Object ret = null;

		String name = method.getName();
		if (name.equals("getRandomInt")) { // intercept the getRandomInt
			ret = methodProxy.invokeSuper(obj, params); // call the base method
			System.out.println("Print random int: " + ret);
		} else if (name.equals("printClassInfo")) { // implement the print class info method
			System.out.println("Class: " + obj.getClass());
		}

		BaseGen baseGen = (BaseGen) obj;
		baseGen.calledMethods.add(name); // change the property

		return ret;
	}

}

public class GenCodeTest {
	public static void main(String[] args) {
		BaseGen worker = DynamicGen.newInstance(BaseGen.class); // generate a BaseGen instance
		int random = worker.getRandomInt();
		System.out.println("You can get an int: " + random); // call the method
		worker.printClassInfo(); // print instance class info
		System.out.println("Called injected methods: " + worker.calledMethods); // print the property
	}
}