package com.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUtils {

    private static final String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            + "|windows (phone|ce)|blackberry"
            + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            + "|laystation portable)|nokia|fennec|htc[-_]"
            + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    private static final String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    //移动设备正则匹配：手机端、平板
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

    public static int getInt(HttpServletRequest request, String param) {
        return getInt(request, param, 0);
    }

    public static final String getString(HttpServletRequest request, String param, String defaultValue) {
        String value = request.getParameter(param);
        return StringUtils.isBlank(value) ? defaultValue : value;
    }

    public static String getString(HttpServletRequest request, String param) {
        return getString(request, param, "");
    }

    public static String getRefer(HttpServletRequest request) {
        return request.getHeader("Referer");
    }

    public static int getInt(HttpServletRequest request, String param, int defaultValue) {
        String value = request.getParameter(param);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        } else {
            return Integer.parseInt(value);
        }
    }

    public static float getFloat(HttpServletRequest request, String param, int defaultValue) {
        String value = request.getParameter(param);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        } else {
            return Float.parseFloat(value);
        }
    }

    public static float getFloat(HttpServletRequest request, String param) {
        return getFloat(request, param, 0);
    }

    public static long getLong(HttpServletRequest request, String param, int defaultValue) {
        String value = request.getParameter(param);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        } else {
            return Long.parseLong(value);
        }
    }

    public static long getLong(HttpServletRequest request, String param) {
        return getLong(request, param, 0);
    }

    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static boolean isRobot(String userAgent) {
        String[] kw_spiders = "bot,crawl,spider,slurp,sohu-search,lycos,robozilla".split(",");
        if (userAgent == null || userAgent.length() == 0) {
            return true;
        }
        if (userAgent.length() < 25) {
            return true;
        }

        String ua = userAgent.toLowerCase();
        for (String kw : kw_spiders) {
            if (ua.contains(kw)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isMobileDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            return true;
        } else {
            return false;
        }
    }


}
