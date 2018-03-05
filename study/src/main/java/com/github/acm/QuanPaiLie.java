package com.github.acm;

import java.util.Arrays;

public class QuanPaiLie {

    public int count;

    public static void main(String[] args) {
        QuanPaiLie quanPaiLie = new QuanPaiLie();
        String[] arr = new String[]{"a", "a", "c", "d"};
        quanPaiLie.arrange(arr, 0, arr.length);
        System.out.println("\ncount: " + quanPaiLie.count);
    }

    public void arrange(String[] arr, int index, int length) {
        if(index == length - 1) {
            System.out.println(Arrays.toString(arr));
            count++;
        } else {
            for (int i = index; i < length; i++) {
                if(isSwap(arr, index, i)) {
                    swap(arr, i,  index);
                    arrange(arr, index + 1, length);
                    swap(arr, i, index);
                }
            }
        }
    }

    public boolean isSwap(String[] arr, int index1, int index2) {
        for(int i = index1; i < index2; i++) {
            if(arr[i].equals(arr[index2])) {
                return false;
            }
        }
        return true;
    }

    private void swap(String[] arr, int index1, int index2) {
        String temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
