package com.github;

import java.util.*;

public class TreeMapStudy {

	public static void main(String... args) {
		SortedMap<String, Integer> sortedMap = new TreeMap<>(Comparator.reverseOrder());
		Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
		Map<String, Integer> hashMap = new HashMap<>();
		for(int i = 0; i < 13; i++) {
			sortedMap.put(String.valueOf((char)(i + 65)), i);
			hashMap.put(String.valueOf((char)(i + 65)), i);
			linkedHashMap.put(String.valueOf((char)(i + 65)), i);
		}
		for(int i = 25; i >= 13; i--) {
			sortedMap.put(String.valueOf((char)(i + 65)), i);
			linkedHashMap.put(String.valueOf((char)(i + 65)), i);
			hashMap.put(String.valueOf((char)(i + 65)), i);
		}
		sortedMap.forEach((k, v) -> System.out.println(k + ":" + v));
		System.out.println();
		linkedHashMap.forEach((k, v) -> System.out.println(k + ":" + v));

		System.out.println();
		hashMap.forEach((k, v) -> System.out.println(k + ":" + v));
	}
}
