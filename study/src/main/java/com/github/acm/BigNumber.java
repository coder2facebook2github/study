package com.github.acm;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 大数加减
 */
public class BigNumber {

	private String value;

	public BigNumber(String value) {
		if(StringUtils.isBlank(value)) {
			throw new NumberFormatException("Can not input a empty string");
		}
		try {
			int i = 0;
			if(value.startsWith("-")){
				i = 1;
			}
			for(; i < value.length(); i++) {
				Integer.parseInt(String.valueOf(value.charAt(i)));
			}
			this.value = value;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public BigNumber plus(BigNumber number) {
		int len = value.length() > number.value.length() ? value.length() + 1 : number.value.length() + 1;
		int[] a = new int[len];
		int[] b = new int[len];
		Arrays.fill(a, 0);
		Arrays.fill(b, 0);
		String tempA = value;
		String tempB = number.value;
		for(int i = len-1; i > 0; i--) {
			a[i] = charAtLast(tempA);
			b[i] = charAtLast(tempB);
			tempA = tempA.substring(0, (tempA.length()-1)<0?0:(tempA.length()-1));
			tempB = tempB.substring(0, (tempB.length()-1)<0?0:(tempB.length()-1));
		}
		System.out.println("A: " + Arrays.toString(a));
		System.out.println("B: " + Arrays.toString(b));
		int[] result = new int[len];
		Arrays.fill(result,0);
		for(int i = len-1; i >= 0; i--) {
			if(a[i] + b[i] + result[i] > 9) {
				result[i-1]=1;
			}
			result[i] = (a[i] + b[i] + result[i]) % 10;
		}
		System.out.println("result: " + Arrays.toString(result));
		StringBuilder resultStr = new StringBuilder();
		for(int i = 0; i < len; i++) {
			resultStr.append(result[i]);
		}
		if(resultStr.toString().startsWith("0")) {
			resultStr = new StringBuilder(resultStr.substring(1));
		}
		return new BigNumber(resultStr.toString());
	}

	public BigNumber minus(BigNumber number) {
		if(value.equals(number.value)) {
			return new BigNumber("0");
		}
		boolean big = true;
		int lenA = value.length();
		int lenB = number.value.length();
		if(lenA < lenB) {
			big = false;
		} else if(lenA == lenB) {
			big = value.compareTo(number.value) > 0;
		}
		int len = value.length() > number.value.length() ? value.length() : number.value.length();
		int[] a = new int[len];
		int[] b = new int[len];
		Arrays.fill(a, 0);
		Arrays.fill(b, 0);
		String tempA = value;
		String tempB = number.value;
		if(!big) {
			tempA = number.value;
			tempB = value;
		}
		for(int i = len-1; i >= 0; i--) {
			a[i] = charAtLast(tempA);
			b[i] = charAtLast(tempB);
			tempA = tempA.substring(0, (tempA.length()-1)<0?0:(tempA.length()-1));
			tempB = tempB.substring(0, (tempB.length()-1)<0?0:(tempB.length()-1));
		}
		System.out.println("A: " + Arrays.toString(a));
		System.out.println("B: " + Arrays.toString(b));
		int[] result = new int[len];
		Arrays.fill(result,0);
		for(int i = len-1; i >= 0; i--) {
			if(a[i] - b[i] < 0) {
				a[i-1]--;
			}
			result[i] = (a[i] - b[i] >= 0 ? a[i] - b[i] : a[i] + 10 - b[i]);
		}
		System.out.println("result: " + (big ? "":"-") + Arrays.toString(result));
		StringBuilder resultBuilder = new StringBuilder();
		for(int i = 0; i < len; i++) {
			resultBuilder.append(result[i]);
		}
		String resultStr = resultBuilder.toString();
		while(resultStr.startsWith("0")) {
			resultStr = resultStr.substring(1);
		}

		return new BigNumber((big?"":"-") + resultStr);
	}

	private int charAtLast(String str) {
		if(StringUtils.isBlank(str)) {
			return 0;
		}
		return Integer.parseInt(String.valueOf(str.charAt(str.length()-1)));
	}

	@Override
	public String toString() {
		return value;
	}

	public static void main(String[] args) {
		BigNumber a = new BigNumber("12279");
		BigNumber b = new BigNumber("12345");
		System.out.println(b.plus(a));
	}
}
