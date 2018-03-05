package com.github.factory.simpleFactory;

import com.github.factory.Car;

public class TestFactory {

	public static void main(String... args) {
		//消费者
		Car car = new SimpleFactory().getCar("Bnw");
		System.out.println(car.getName());
	}
}
