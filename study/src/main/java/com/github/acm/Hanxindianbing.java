package com.github.acm;


/**
 * 相传韩信才智过人，从不直接清点自己军队的人数，只要让士兵先后以三人一
 * 排、五人一排、七人一排地变换队形，而他每次只掠一眼队伍的排尾就知道总
 * 人数了。输入3个非负整数a,b,c ，表示每种队形排尾的人数（a<3,b<5,c<7），
 * 输出总人数的最小值（或报告无解）。已知总人数不小于10，不超过100 。
 */
public class Hanxindianbing {


	public static void main(String... args) {
		System.out.println(countShibin(2, 1, 6));
	}


	public static Integer countShibin(int a, int b, int c) {
		for(int i = 10; i < 100; i++) {
			int j = 0;
			if(i % 3 == a)
				j++;
			if(i % 5 == b)
				j++;
			if(i % 7 == c)
				j++;
			if(j == 3){
				return i;
			} else {
				j = 0;
			}
		}

		return null;
	}
}
