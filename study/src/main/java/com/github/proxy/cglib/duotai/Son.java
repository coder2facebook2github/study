package com.github.proxy.cglib.duotai;

public class Son extends Father {

	@Override
	public void print() {
		System.out.println("This is son");
	}

	public void sonPrint(){
		System.out.println("This is only son print");
	}
}
