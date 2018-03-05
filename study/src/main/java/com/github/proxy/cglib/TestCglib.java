package com.github.proxy.cglib;

public class TestCglib {

	public static void main(String... args) {
		try {
			Person object = new Meipo().getInstance(Person.class);
			object.findLove();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
