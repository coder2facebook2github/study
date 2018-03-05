package com.github;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListUtils {



	public static void main(String... args) {
		List<String> list = new ArrayList<>();
		list.add("abc");
		list.add("bcd");
		list.add("bcd");
		list.add("bcd");
		list.add("cde");
		list.add("cde");
		Set<String> set = checkDuplicateValue(list);
		for(String value : set) {
			System.out.println(value);
		}
	}

	/**
	 * 找出一个List 中重复的值
	 * @param list
	 * @return
	 */
	public static Set<String> checkDuplicateValue(List<String> list) {
		Set<String> set = new HashSet<>();
		Set<String> duplicateValue = new HashSet<>();
		int setSize = set.size();
		for(String value : list) {
			set.add(value);
			if(set.size() == setSize) {
				duplicateValue.add(value);
			}
			setSize = set.size();
		}
		return duplicateValue;
	}



}
