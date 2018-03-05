package com.github.acm;

import java.util.*;


/**
 * Date:        11:18 08/25/2017
 * Version:     1.0
 * Description: 打印二维数组，按照顺时针，由外到内
 */
public class PrintArray {
	private static List<Way> record = new ArrayList<>();

	public static void main(String... args) {
		int[][] arr = Huaxue.createArr(6, 6);
		printWay(arr);
	}

	public static void printWay(int[][] arr) {
		int a = 0;
		int b = 0;
		Direction direction = Direction.RIGHT;
		Map<String, Object> nextStep;
		do {
			System.out.print(arr[a][b] + ", ");
			nextStep = getNextStep(arr, a, b, direction);
			if(!(boolean) nextStep.get("end")){
				a = ((Way) nextStep.get("nextStep")).a;
				b = ((Way) nextStep.get("nextStep")).b;
				direction = (Direction) nextStep.get("direction");
			}
		} while (!(boolean) nextStep.get("end"));
	}

	public static Map<String, Object> getNextStep(int[][] arr, int a, int b, Direction direction) {
		Map<String, Object> result = new HashMap<>();
		int height = arr.length;
		int width = arr[0].length;
		switch (direction) {
			case UP:
				if (!record.contains(new Way(a - 1, b))) {
					result.put("nextStep", new Way(a - 1, b));
					result.put("direction", Direction.UP);
					result.put("end", false);
				} else if (!record.contains(new Way(a, b + 1))) {
					result.put("nextStep", new Way(a, b + 1));
					result.put("direction", Direction.RIGHT);
					result.put("end", false);
				} else if (record.contains(new Way(a, b + 1))) {
					result.put("end", true);
				}
				break;
			case DOWN:
				if (a + 1 <= height - 1) {
					if (!record.contains(new Way(a + 1, b))) {
						result.put("nextStep", new Way(a + 1, b));
						result.put("direction", Direction.DOWN);
						result.put("end", false);
					} else if (record.contains(new Way(a + 1, b))) {
						if (!record.contains(new Way(a, b - 1))) {
							result.put("nextStep", new Way(a, b - 1));
							result.put("direction", Direction.LEFT);
							result.put("end", false);
						} else if (record.contains(new Way(a, b - 1))) {
							result.put("end", true);
						}
					}
				} else {
					result.put("nextStep", new Way(a, b - 1));
					result.put("direction", Direction.LEFT);
					result.put("end", false);
				}
				break;
			case LEFT:
				if (b - 1 >= 0) {
					if (!record.contains(new Way(a, b - 1))) {
						result.put("nextStep", new Way(a, b - 1));
						result.put("direction", Direction.LEFT);
						result.put("end", false);
					} else if (record.contains(new Way(a, b - 1))) {
						if (!record.contains(new Way(a - 1, b))) {
							result.put("nextStep", new Way(a - 1, b));
							result.put("direction", Direction.UP);
							result.put("end", false);
						} else if (record.contains(new Way(a - 1, b))) {
							result.put("end", true);
						}
					}
				} else {
					if (record.contains(new Way(a - 1, b))) {
						result.put("end", true);
					} else if (!record.contains(new Way(a - 1, b))) {
						result.put("nextStep", new Way(a - 1, b));
						result.put("direction", Direction.UP);
						result.put("end", false);
					}
				}
				break;
			case RIGHT:
				if (b + 1 <= width - 1) {
					if (!record.contains(new Way(a, b + 1))) {
						result.put("nextStep", new Way(a, b + 1));
						result.put("direction", Direction.RIGHT);
						result.put("end", false);
					} else if (record.contains(new Way(a, b + 1))) {
						if (!record.contains(new Way(a + 1, b))) {
							result.put("nextStep", new Way(a + 1, b));
							result.put("direction", Direction.DOWN);
							result.put("end", false);
						} else if (record.contains(new Way(a + 1, b))) {
							result.put("end", true);
						}
					}
				} else {
					result.put("nextStep", new Way(a + 1, b));
					result.put("direction", Direction.DOWN);
					result.put("end", false);
				}
				break;
		}
		record.add(new Way(a, b));

		return result;
	}


	enum Direction {
		UP, DOWN, LEFT, RIGHT
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
			return "[" + a + "," + b + "]";
		}
	}

}
