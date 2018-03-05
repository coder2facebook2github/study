package com.github.factory.func;

import com.github.factory.Benc;
import com.github.factory.Car;

public class BencFactory implements Factory {
	@Override
	public Car getCar() {
		return new Benc();
	}
}
