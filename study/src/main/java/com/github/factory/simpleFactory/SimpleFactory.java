package com.github.factory.simpleFactory;

import com.github.factory.Audi;
import com.github.factory.Benc;
import com.github.factory.Bnw;
import com.github.factory.Car;

public class SimpleFactory {


	public Car getCar(String name) {
		if("Bnw".equalsIgnoreCase(name)){
			return new Bnw();
		} else if("Benc".equalsIgnoreCase(name)){
			return new Benc();
		} else if("Audi".equalsIgnoreCase(name)){
			return new Audi();
		} else {
			System.out.println("没有该汽车");
			return null;
		}
	}
}
