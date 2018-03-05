package com.github.acm;


import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串，查找包含所有元素的最短子串
 */
public class MinSubString {

    public static void main(String[] args) {
        System.out.println(substring("288811"));
    }




    public static String substring(String value) {
        if(StringUtils.isBlank(value)) {
            return "";
        }
        Set<String> set = new HashSet<>();
        for(int i = 0; i < value.length(); i++) {
            set.add(String.valueOf(value.charAt(i)));
        }
        String result = "";
        int len = set.size();
        for(int i = len; i <= value.length(); i++) {
            for(int j = 0; j < value.length() - i + 1; j++) {
                Set<String> set1 = new HashSet<>();
                for(int k = j; k < j+i; k++) {
                    set1.add(String.valueOf(value.charAt(k)));
                }
                System.out.println(value.substring(j, j+i));
                if(set.size() == set1.size() && set.containsAll(set1)) {
                    result = value.substring(j, j+i);
                    return result;
                }
            }
        }
        return "";
    }
}
