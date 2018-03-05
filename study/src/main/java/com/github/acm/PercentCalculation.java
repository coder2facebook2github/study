package com.github.acm;

/**
 * 分数加减
 */
public class PercentCalculation {
	private String value;
	/** 分子 **/
	private int numerator;
	/** 分母 **/
	private int denominator;

	public PercentCalculation(String value) {
		this.value = value;
		if("0".equals(value)) {
			return;
		}
		if(value.contains("/")){
			this.denominator = Integer.parseInt(value.split("/")[1]);
			this.numerator = Integer.parseInt(value.split("/")[0]);
		} else {
			this.denominator = 1;
			this.numerator = Integer.parseInt(value);
		}
		if(numerator == denominator) {
			this.value = "1";
		}
	}

	public PercentCalculation plus(PercentCalculation digit) {
		int minMultiple = minMultiple(denominator, digit.denominator);
		numerator = minMultiple / denominator * numerator;
		digit.numerator = minMultiple / digit.denominator * digit.numerator;
		int resultNumerator = numerator + digit.numerator;
		int maxDivisor = maxDivisor(resultNumerator, minMultiple);
		PercentCalculation result = new PercentCalculation((resultNumerator/maxDivisor) + "/" + minMultiple/maxDivisor);
		return result;
	}

	public PercentCalculation minus(PercentCalculation digit) {
		int minMultiple = minMultiple(denominator, digit.denominator);
		numerator = minMultiple / denominator * numerator;
		digit.numerator = minMultiple / digit.denominator * digit.numerator;
		int resultNumerator = numerator - digit.numerator;
		if(resultNumerator == 0) {
			return new PercentCalculation("0");
		}
		int maxDivisor = maxDivisor(Math.abs(resultNumerator), minMultiple);
		PercentCalculation result = new PercentCalculation((resultNumerator/maxDivisor) + "/" + minMultiple/maxDivisor);
		return result;
	}



	private int minMultiple(int a, int b) {
		return a * b / maxDivisor(a, b);
	}

	private int maxDivisor(int a, int b) {
		int i = a <= b ? a : b;
		for(; i >= 1; i--) {
			if(a % i == 0 && b % i == 0) {
				return i;
			}
		}
		return 1;
	}

	@Override
	public String toString() {
		return value;
	}

	public static void main(String[] args) {
		PercentCalculation a = new PercentCalculation("2");
		PercentCalculation b = new PercentCalculation("8/4");
		System.out.println(a.minus(b));
	}
}
