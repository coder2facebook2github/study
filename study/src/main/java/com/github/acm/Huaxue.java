package com.github.acm;

import java.util.*;

/**
 * Date:        16:10 08/24/2017
 * Version:     1.0
 * Description: Michael喜欢滑雪百这并不奇怪， 因为滑雪的确很刺激。可是为了获得速度，滑的区域必须向下倾斜，
 * 而且当你滑到坡底，你不得不再次走上坡或者等待升降机来载你。Michael想知道载一个区域中最长底滑坡。区域由一
 * 个二维数组给出。数组的每个数字代表点的高度。下面是一个例子
	 1  2  3  4 5

	 16 17 18 19 6

	 15 24 25 20 7

	 14 23 22 21 8

	 13 12 11 10 9

	 一个人可以从某个点滑向上下左右相邻四个点之一，当且仅当高度减小。在上面的例子中，一条可滑行的滑坡为
 		24-17-16-1。当然25-24-23-…-3-2-1更长。事实上，这是最长的一条。
 */
public class Huaxue {
	final static int[][] arr = new int[][]{
			{1, 2, 3, 4},
			{8, 7, 6, 5},
			{9, 10,11,12},
			{16,15,14,13}};

	public static void main(String... args) {
//		System.out.println((int)(checkHasNext(arr, 2, 2).get("count")) > 0);
//		System.out.println(getLength(arr, 3, 0));
		int[][] arr2 = createArr(6, 6);
		getMaxLength(arr2);
	}

	public static int[][] createArr(int height, int width) {
		int[][] result = new int[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				result[i][j] = new Random().nextInt(999) + 1;
			}
			System.out.println(Arrays.toString(result[i]));
		}
		System.out.println();
		return result;
	}

	public static int[][][] getMaxLength(int[][] arr) {
		int[][][] result = new int[3][arr.length][arr[0].length];
		int max = 0;
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				int length = getLength(arr, i, j);
				if(length > max) {
					max = length;
					result[2][0][0] = max;
					result[2][0][1] = i;
					result[2][0][2] = j;
				}
				System.out.print(length + ", ");
				result[0][i][j] = length;
				result[1][i][j] = arr[i][j];
			}
			System.out.println();
		}
		printWay(result);
		return result;
	}

	public static void printWay(int[][][] arr) {
		List<Way> wayIndex = new ArrayList<>();
		int a = arr[2][0][1];
		int b = arr[2][0][2];
		int height = arr[2][0][0];
		do{
			wayIndex.add(new Way(a, b));
			Way nextStep = (Way)checkHasNext(arr[0], a, b).get("nextStep");
			a = nextStep.a;
			b = nextStep.b;
			height--;
		}while (height > 0);
		wayIndex.add(new Way(a, b));
		System.out.println();
		for(int i = 0; i < wayIndex.size(); i++) {
			System.out.print(wayIndex.get(i));
		}
		System.out.println("\n");
		for(int i = 0; i < arr[1].length; i++) {
			for(int j = 0; j < arr[1][0].length; j++) {
				if(wayIndex.contains(new Way(i, j))){
					if(arr[1][i][j] < 10) {
						System.out.print("  ");
					}
					if(10 <= arr[1][i][j] && arr[1][i][j] < 100) {
						System.out.print(" ");
					}
					System.out.print(arr[1][i][j] + ",");
				} else {
					System.out.print(" * ,");
				}
			}
			System.out.println();
		}
		System.out.println();
		for(int i = 0; i < arr[1].length; i++) {
			for(int j = 0; j < arr[1][0].length; j++) {
				if(wayIndex.contains(new Way(i, j))){
					if(arr[0][i][j] < 10) {
						System.out.print("  ");
					}
					if(10 <= arr[0][i][j] && arr[0][i][j] < 100) {
						System.out.print(" ");
					}
					System.out.print(arr[0][i][j] + ",");
				} else {
					System.out.print(" * ,");
				}
			}
			System.out.println();
		}
	}


	static class Way {
		protected int a = -1;
		protected int b = -1;

		public Way() {
		}

		public Way(int a, int b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Way way = (Way) o;
			return a == way.a &&
					b == way.b;
		}

		@Override
		public int hashCode() {
			return Objects.hash(a, b);
		}

		@Override
		public String toString() {
			return "[" +a + "," + b + "]";
		}
	}

	public static int getLength(int[][] arr, int a, int b) {
		Map<String, Object> map = checkHasNext(arr, a, b);
		if((int)(map.get("count")) == 0){
			return 0;
		} else {
			boolean up = (boolean)map.get("up");
			boolean down = (boolean)map.get("down");
			boolean left = (boolean)map.get("left");
			boolean right = (boolean)map.get("right");
			return maxValue(up ? getLength(arr, a-1, b) : 0,
							down ? getLength(arr, a+1, b) : 0,
							left ? getLength(arr, a, b-1) : 0,
							right ? getLength(arr, a, b+1) : 0) + 1;
		}
	}

	public static int maxValue(int a, int b, int c, int d) {
		return Math.max(Math.max(a, b), Math.max(c, d));
	}

	public static Map<String, Object> checkHasNext(int[][] arr, int a, int b) {
		int height = arr.length;
		int width = arr[0].length;
		int count = 0;
		Map<String, Object> result = new HashMap<>();
		result.put("up", false);
		result.put("down", false);
		result.put("left", false);
		result.put("right", false);

		if(a - 1 >= 0) {
			if(arr[a-1][b] < arr[a][b]) {
				count++;
				result.put("up", true);
				if(arr[a][b] - arr[a-1][b] == 1) {
					result.put("nextStep", new Way(a-1, b));
				}
			}
		}
		if(a + 1 <= height - 1) {
			if(arr[a + 1][b] < arr[a][b]){
				count++;
				result.put("down", true);
				if(arr[a][b] - arr[a+1][b] == 1) {
					result.put("nextStep", new Way(a+1, b));
				}
			}
		}
		if(b - 1 >= 0)  {
			if(arr[a][b-1] < arr[a][b]) {
				count++;
				result.put("left", true);
				if(arr[a][b] - arr[a][b-1] == 1) {
					result.put("nextStep", new Way(a, b-1));
				}
			}
		}
		if(b + 1 <= width - 1) {
			if(arr[a][b+1] < arr[a][b]) {
				count++;
				result.put("right", true);
				if(arr[a][b] - arr[a][b+1] == 1) {
					result.put("nextStep", new Way(a, b+1));
				}
			}
		}
		result.put("count", count);
		return result;
	}
}
