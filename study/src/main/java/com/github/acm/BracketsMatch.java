package com.github.acm;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date:        10:56 08/24/2017
 * Version:     1.0
 * Description: 输入一串全是括号的字符串，检查括号是否匹配
 */
public class BracketsMatch {

	final static String BracketsString1 = "{{}[()]}([])";
	final static String BracketsString2 = "{{}[({)]}}([])";
	final static String BracketsString3 = "}{{}[()]}([]){";

	public static void main(String... args) {
		System.out.println(checkMatch(BracketsString1));
		System.out.println(checkMatch(BracketsString2));
		System.out.println(checkMatch(BracketsString3));
	}

	public static boolean checkMatch(String brackets) {
		List<Character> bracketList = new ArrayList<>();
		for (int i = 0; i < brackets.length(); i++) {
			bracketList.add(brackets.charAt(i));
		}

		int beginLength;
		int endLength;
		do {
			beginLength = bracketList.size();
//			System.out.println("========== begin:" + Arrays.toString(bracketList.toArray()));
			for (int i = 0; i < bracketList.size() - 1; i++) {
				if (bracketList.get(i) == '{') {
					if (bracketList.get(i + 1) == '}') {
						bracketList.set(i, '0');
						bracketList.set(i + 1, '0');
					}
				} else if (bracketList.get(i) == '[') {
					if (bracketList.get(i + 1) == ']') {
						bracketList.set(i, '0');
						bracketList.set(i + 1, '0');
					}
				} else if (bracketList.get(i) == '(') {
					if (bracketList.get(i + 1) == ')') {
						bracketList.set(i, '0');
						bracketList.set(i + 1, '0');
					}
				}
			}
			bracketList.removeIf((c) -> c == '0');
			endLength = bracketList.size();
//			System.out.println("========== end:" + Arrays.toString(bracketList.toArray()));
		} while (beginLength > endLength);
		if (beginLength == 0) {
			return true;
		}
		return false;
	}
}
