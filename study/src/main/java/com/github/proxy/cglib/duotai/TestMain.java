package com.github.proxy.cglib.duotai;

public class TestMain {

	public static void main(String... args) {
		Father father = new Son();
		father.print();

		((Father)new Son()).fatherPrint();

		print((Father) new Son());

//		printf((Son)new Father());
	}

	public static void print(Father obj) {
		obj.print();
	}

	public static void printf(Son son) {
		son.print();
	}
}
