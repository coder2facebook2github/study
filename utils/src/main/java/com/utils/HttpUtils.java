package com.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpUtils {

    public static void responseJsonp(HttpServletResponse response, String s, String jsonpCallback) throws IOException {
        response(response, jsonpCallback + "(" + s + ")", "text/plain");
    }

    public static void response(HttpServletResponse response, String s) throws IOException {
        response(response, s, "text/json");
    }

    public static void response(HttpServletResponse response, String s, String contentType) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(contentType);
        PrintWriter out = response.getWriter();
        out.println(s);
        out.flush();
        out.close();
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}
