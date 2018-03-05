package com.github.proxy.self_proxy;

public class TestSelfProxy {
	public static void main(String... args) {
		Person person = (Person)new SelfMeipo().getInstance(new Xiaoming());

		person.findLove();


	}
}
