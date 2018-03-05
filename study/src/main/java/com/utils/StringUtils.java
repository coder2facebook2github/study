package com.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.io.UnsupportedEncodingException;

import static org.apache.commons.codec.CharEncoding.UTF_8;

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
