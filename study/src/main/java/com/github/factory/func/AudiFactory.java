package com.github.factory.func;

import com.github.factory.Audi;
import com.github.factory.Car;

public class AudiFactory implements Factory {
	@Override
	public Car getCar() {
		return new Audi();
	}
}
