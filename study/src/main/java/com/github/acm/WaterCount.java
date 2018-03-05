package com.github.acm;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 校园里有一些小河和一些湖泊，现在，我们把它们通一看成水池，假设有一张我们学校的某处的地图，
 * 这个地图上仅标识了此处是否是水池，现在，你的任务来了，请用计算机算出该地图中共有几个水池。
 * 给定一个二维数组，1，表示陆地，0表示水池
 * 每个水池的旁边（上下左右四个位置）如果还是水池的话的话，它们可以看做是同一个水池
 */
public class WaterCount {

	public static void main(String[] args) {
		Integer[][] land = new Integer[][]{
				{1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
				{1, 1, 0, 1, 0, 1, 1, 1, 1, 1},
				{1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
				{1, 0, 1, 1, 0, 1, 1, 0, 0, 1},
				{0, 1, 1, 0, 0, 1, 1, 1, 1, 0},
				{0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
				{0, 0, 1, 1, 1, 1, 1, 1, 0, 1},
				{1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
				{0, 0, 1, 0, 1, 1, 1, 1, 1, 1},
				{1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
		};
		Integer[][] land2 = productWater(8, 8, 66);
		System.out.println(countWater(land2));
	}


	public static Integer[][] productWater(int row, int column, int percent) {
		Integer[][] result = new Integer[row][column];
		for (int j = 0; j < row; j++) {
			for (int i = 0; i < column; i++) {
				long currentMillis = System.currentTimeMillis();
				long currentNano = System.nanoTime();
				String randomStr = currentMillis + currentNano + "" + (char) ((currentMillis + currentNano) % 26 + 65) + new Random().nextLong() + "";
				String hexStr = getStr(DigestUtils.md5Hex(randomStr));
				Random random = new Random(Long.parseLong(hexStr, 16));
				if (random.nextInt(10000) + 1 <= percent * 100) {
					result[j][i] = 1;
				} else {
					result[j][i] = 0;
				}
			}
		}
		for (int j = 0; j < row; j++) {
			for (int i = 0; i < column; i++) {
				System.out.print(result[j][i] + " ");
			}
			System.out.println();
		}

		return result;
	}

	public static String getStr(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < str.length(); i = i + 3) {
			stringBuilder.append(str.charAt(i));
		}
		String result = stringBuilder.toString();
		return result;
	}

	public static int countWater(Integer[][] land) {
		List<Set<Position>> list = new ArrayList<>();
		for (int i = 0; i < land.length; i++) {
			for (int j = 0; j < land[0].length; j++) {
				int value = land[i][j];
				if (value == 0) {
					int count = 0;
					Set<Position> has = new HashSet<>();
					int index = 0;
					List<Integer> indexList = new ArrayList<>();
					for (Set<Position> s : list) {
						for (Position p : s) {
							if (Math.abs(i - p.row) + Math.abs(j - p.column) == 1) {
								Position position = new Position(i, j);
								has.add(position);
								count++;
								index = list.indexOf(s);
								indexList.add(list.indexOf(s));
							}
						}
					}
					if (count == 1) {
						list.get(index).addAll(has);
					} else if(count == 2 && (indexList.get(0) != indexList.get(1))){
						int index2 = indexList.get(1);
						Set<Position> first = list.get(indexList.get(0));
						Set<Position> second = list.get(indexList.get(1));
						list.remove(index2);
						first.addAll(second);
						first.add(new Position(i, j));
					} else if(count == 2 && (indexList.get(0) == indexList.get(1))){
						list.get(index).addAll(has);
					} else {
						Set<Position> set = new HashSet<>();
						set.add(new Position(i, j));
						list.add(set);
					}
				}
			}
		}

		for (Set<Position> s : list) {
			for (Position p : s) {
				System.out.print(p.row + "," + p.column + " ");
			}
			System.out.println("\t SIZE: " + s.size());
		}

		return list.size();
	}

	static class Position {
		public int row;
		public int column;

		public Position() {
		}

		public Position(int row, int column) {
			this.row = row;
			this.column = column;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Position position = (Position) o;
			return row == position.row &&
					column == position.column;
		}

		@Override
		public int hashCode() {
			return Objects.hash(row, column);
		}
	}
}
