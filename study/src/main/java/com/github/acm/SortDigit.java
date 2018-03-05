package com.github.acm;

import java.io.*;

public class SortDigit {


    public static void main(String[] args) throws IOException {
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        primeArrange(a, 20);

    }

    public static void primeArrange(int[] arr, int begin, int length) {
        BufferedWriter writer = null;
        if (begin == length) {
            if (isPrime(arr[begin - 1] + arr[0])) {
                try {
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("prime.txt", true)));
                    writer.write(arrayString(arr));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(writer != null){
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return;
        } else if (begin > 1 && !isPrime(arr[begin - 1] + arr[begin - 2])) {
            return;
        } else {
            for (int i = begin; i < length; i++) {
                swap(arr, begin, i);
                primeArrange(arr, begin + 1, length);
                swap(arr, begin, i);
            }
        }
    }

    private static String arrayString(int[] arr) {
        StringBuilder value = new StringBuilder("");
        for(int j = 0; j < 20; j++) {
            if(j < 19 && ((arr[j] < 10 && arr[j+1] < 10) || (arr[j] >= 10 && arr[j+1] >= 10))) {
                value.append(arr[j]);
            } else if(j < 19){
                value.append(arr[j] + " ");
            } else {
                value.append(arr[j] + "\n");
            }
        }
        return value.toString();
    }

    private static void swap(int[] x, int a, int b) {
        int temp = x[a];
        x[a] = x[b];
        x[b] = temp;
    }

    public static void primeArrange(int[] arr, int length) {
        if (arr == null || length <= 0) {
            return;
        }
        primeArrange(arr, 1, length);
    }

    private static boolean isPrime(int value) {
        int i;
        for (i = 2; i <= Math.sqrt(value); i++) {
            if (value % i == 0) {
                return false;
            }
        }
        if (i > Math.sqrt(value)) {
            return true;
        }
        return false;
    }
}