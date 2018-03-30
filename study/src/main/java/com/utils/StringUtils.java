package com.utils;

import java.io.UnsupportedEncodingException;

/**
 * Date:        13:24 08/03/2017
 * Version:     1.0
 * Description: ${Description}
 */
public class StringUtils {

    public static void main(String... args) throws UnsupportedEncodingException {
//		SimpleHash simpleHash = new SimpleHash("md5", "abc", "a");
//
//		System.out.println(simpleHash.toHex());
//
//		System.out.println(DigestUtils.md5Hex("abc"));

        String str = "北京市";

        System.out.println(str.replace("市", ""));

    }
}
