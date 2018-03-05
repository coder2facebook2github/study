package com.github.acm;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitString {

    public static void main(String[] args) {
        String value = "1 2 3 4 7 6 11 12 19 10 13 16 15 14 5 8 9 20 17 18";
        String value2 = "1 2 3 4 7 6 11 8 15 14 9 20 17 12 5 18 13 10 19 16";
        Integer[] result = splitDigit(value2);
        System.out.println(checkArr(result));


    }

    public static boolean checkArr(Integer[] arr) {
        for(int i = 0; i < arr.length - 1; i++) {
            if(!isPrime(arr[i] + arr[i+1])) {
                System.out.println(arr[i] + ", " + arr[i+1]);
                return false;
            }
        }
        if(!isPrime(arr[0] + arr[19]))
            return false;
        return true;
    }


    public static Integer[] splitDigit(String value) {
        if (org.apache.commons.lang3.StringUtils.isBlank(value)) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (((int) ch) >= 48 && ((int) ch) <= 57) {
                builder.append(ch);
            } else {
                if(builder.toString().trim().length() > 0) {
                    list.add(Integer.parseInt(builder.toString()));
                    builder = new StringBuilder("");
                }
            }
        }
        if(builder.toString().trim().length() > 0) {
            list.add(Integer.parseInt(builder.toString()));
        }
        Integer[] result = new Integer[list.size()];
        return list.toArray(result);
    }

    private static boolean isPrime(int value) {
        int i;
        for (i = 2; i < Math.sqrt(value); i++) {
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
