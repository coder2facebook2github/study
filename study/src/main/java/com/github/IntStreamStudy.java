package com.github;


import java.util.function.Predicate;
import java.util.stream.IntStream;

public class IntStreamStudy {

	public static void main(String... args) {
		intStreamRange();
		intStreamBuilder();

	}
	public static void intStreamRange(){
		IntStream.range(1, 10).forEach(value -> System.out.println(value));
		IntStream.rangeClosed(1, 10).forEach(value -> System.out.println(value));
	}
	public static void intStreamBuilder(){
		IntStream.builder().add(1).add(5).build().forEach(value -> System.out.println(value));
	}



	public static void intStreamFilter() {
		IntStream.rangeClosed(6, 1000).filter(value -> value % 2 == 0)
				.forEach(value -> {

				});

	}

}
