package com.github.acm;

import java.util.Arrays;
import java.util.Random;

/**
 * Date:        18:25 08/23/2017
 * Version:     1.0
 * Description: 对一个数组进行排序，奇数在左边，偶数在右边
 */
public class SortArray {

	public static void main(String... args) {
		Integer[] arr = createArray(30);
		System.out.println(Arrays.toString(arr) + "\t" + arr);
//		Integer[] arr2 = createArray(10);
//		System.out.println(Arrays.toString(arr2));
//		arr = arr2;
//
//		System.out.println("=================");
//		System.out.println(Arrays.toString(arr));
//		System.out.println(Arrays.toString(arr2));

		sortArray(arr);
		System.out.println(Arrays.toString(arr) + "\t" + arr);
	}



	public static Integer[] createArray(int length){
		if(length > 0){
			Integer[] result = new Integer[length];
			for(int i = 0; i < length; i++) {
				result[i] = new Random().nextInt(101) + 1;
			}
			return result;
		}
		return null;
	}


	public static void sortArray(Integer [] arr) {
		Integer[] tempArr = new Integer[arr.length];
		Arrays.fill(tempArr, null);
		int begin = 0;
		int end = arr.length - 1;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] % 2 == 0){
				tempArr[end] = arr[i];
				end--;
			} else {
				tempArr[begin] = arr[i];
				begin++;
			}
		}
		for(int i = 0; i <arr.length; i++) {
			arr[i] = tempArr[i];
		}
	}
}
