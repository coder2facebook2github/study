package com.github.factory.func;

import com.github.factory.Bnw;
import com.github.factory.Car;

public class BnwFactory implements Factory {
	@Override
	public Car getCar() {
		return new Bnw();
	}
}
