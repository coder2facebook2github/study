package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Java10Study {


    public static void main(String[] args) {
        var a = 10;
        System.out.println(a);
        var b = new BufferedReader(new InputStreamReader(Java10Study.class.getResourceAsStream("/a.txt"), StandardCharsets.UTF_8));
        var c = "";
        try {
            while ((c = b.readLine()) != null) {
                System.out.println(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
