package com.spring.boot.study.common.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {


    public static void main(String[] args) {
        String str = "/redis///get*//name////";
        System.out.println(str);
//        String pattern = "^(/redis/)\\S*$";
//        String pattern = "^(\\S*)/+$";
        String pattern = "^(/redis/)\\S*$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
        String str1 = str.replaceAll("/+$", "");
        System.out.println(str1);
        String str2 = str1.replaceAll("/+", "/");
        System.out.println(str2);
        String str3 = str2.replaceAll("\\**$", "");
        System.out.println(str3);
    }
}
