package com.github.factory.func;

public class TestFactory {

	public static void main(String... args) {
		Factory factory = new AudiFactory();
		System.out.println(factory.getCar().getName());
	}
}
