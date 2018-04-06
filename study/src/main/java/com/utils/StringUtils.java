package com.utils;

import java.io.UnsupportedEncodingException;

/**
 * Date:        13:24 08/03/2017
 * Version:     1.0
 * Description: ${Description}
 */
public class StringUtils {

    public static void main(String[] args) {
        int a = 'M';
        int b = 'Y';
        int c = 'A';
        System.out.println(a);
        System.out.println(c -64);

        System.out.println((a + b - 64 - 64) * (b - a));
    }
}
