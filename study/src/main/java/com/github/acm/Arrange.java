package com.github.acm;

import java.util.Arrays;

public class Arrange {

    public static void main(String[] args) {
        fullArray(new int[]{1, 2, 3, 4, 5}, 0, 4);
    }

    public static void fullArray(int[] array, int cursor, int end) {
        if(cursor == end) {
            System.out.println(Arrays.toString(array));
        } else {
            for(int i = cursor; i <= end; i++) {
                swap(array, cursor, i);
                fullArray(array, cursor + 1, end);
                swap(array, cursor, i);
            }
        }
    }

    private static void swap(int[] x, int a, int b) {
        int temp = x[a];
        x[a] = x[b];
        x[b] = temp;
    }
}