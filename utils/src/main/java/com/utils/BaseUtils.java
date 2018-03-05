package com.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * @author ripflowers
 */
public class BaseUtils {

    private static final String UNCHECKED = "unchecked";
    public static final int DEFAULT_COOKIE_TIME = 30 * 24 * 60 * 60;

    public static String cleanXSS(String s) {
        s = s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        s = s.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        s = s.replaceAll("'", "&#39;");
        s = s.replaceAll("eval\\((.*)\\)", "");
        s = s.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
        s = s.replaceAll("script", "&#115;cript");
        s = s.replaceAll("\"", "&#34;");
        return s;
    }

    /**
     * 中文参数解码
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decode(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一段字符的长度，输入长度中汉、日、韩文字符长度为2
     */
    public static int getCharsLength(char[] chars) {
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            count += getSpecialCharLength(chars[i]);
        }
        return count;
    }

    /**
     * 获取指定的cookie内容
     *
     * @param request
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return "";
    }

    public static float getFloatValueFromMap(Map<String, Object> map, String key) {
        float v = 0;
        if (map.get(key) != null) {
            try {
                v = Float.parseFloat(map.get(key).toString());
            } catch (Exception e) {
                v = 0;
            }
        }
        return v;
    }

    public static float getFloatValueFromMap(Map<String, Object> map, String key,float defaultValue) {
        float v = getFloatValueFromMap(map,key) ;
        v = v==0?defaultValue:v;
        return v;
    }

    public static int getIntValueFromMap(Map<String, Object> map, String key) {
        int v = 0;
        if (map.get(key) != null) {
            try {
                v = Integer.parseInt(map.get(key).toString());
            } catch (Exception e) {
                v = 0;
            }
        }
        return v;
    }

    public static long getLongValueFromMap(Map<String, Object> map, String key) {
        long v = 0;
        if (map.get(key) != null) {
            try {
                v = Long.parseLong(map.get(key).toString());
            } catch (Exception e) {
                v = 0;
            }
        }
        return v;
    }

    public static long getLongValueFromMap(Map<String, Object> map, String key, long defaultVal) {
        long v = -1;
        if (map.get(key) != null) {
            try {
                v = Long.parseLong(map.get(key).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return v;
    }

    public static boolean getBooleanValueFromMap(Map<String, Object> map, String key) {
        boolean v = false;
        if (map.get(key) != null) {
            try {
                return (boolean)map.get(key) || Boolean.parseBoolean(map.get(key).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return v;
    }

    public static int getIntValueFromMap(Map<String, Object> map, String key,int defaultValue) {
        int v = getIntValueFromMap(map,key) ;
        v = v==0?defaultValue:v;
        return v;
    }

    /**
     * 获取ip
     *
     * @param req
     * @return
     */
    public static String getIP(HttpServletRequest req) {
        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }

    public static Map<String, Object> getMapParam(String key, Object value) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(key, value);
        return param;
    }

    /**
     * 获取字符长度：汉、日、韩文字符长度为2，ASCII码等字符长度为1
     *
     * @param c 字符
     * @return 字符长度
     */
    public static int getSpecialCharLength(char c) {
        if (isLetter(c)) {
            return 1;
        } else {
            return 2;
        }
    }

    public static String getStringValueFromMap(Map<String, Object> map, String key) {
        Object v = map.get(key);
        if (v == null || StringUtils.isBlank(v.toString())) {
            v = "";
        }
        return v.toString();
    }

    public static String[] intArrayToStrArray(int[] ints) {
        String[] strIds = new String[ints.length];
        for (int i = 0; i < ints.length; i++) {
            strIds[i] = String.valueOf(ints[i]);
        }
        return strIds;
    }

    public static boolean isEmpty(Collection<?> co) {
        if (co != null && !co.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isEmpty(Map<?, ?> map) {
        if (map != null && map.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 判断1个char是否是数字或者小数点
     *
     * @param c
     * @return
     */
    public static boolean isNumberOrPoint(char c) {
        if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8'
                || c == '9' || c == '.') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断1个char是否是标点符号
     *
     * @param c
     * @return
     */
    public static boolean isSymbol(char c) {
        if (c == ',' || c == '.' || c == '?' || c == '!' || c == '"' || c == ';' || c == '，' || c == '。' || c == '？'
                || c == '！' || c == '“' || c == '；') {
            return true;
        } else {
            return false;
        }
    }

    public static Object loadBeanFromMap(Object object, Map<String, Object> map) {
        Class cls = object.getClass();
        Method[] methods = cls.getMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("set")) {
                String setFieldName = m.getName().substring(3);
                setFieldName = setFieldName.substring(0, 1).toLowerCase() + setFieldName.substring(1);
                if (map.get(setFieldName) != null) {
                    try {
                        //System.out.println(setFieldName);
                        if (map.get(setFieldName) instanceof Boolean) {
                            if ("int".equals(m.getParameterTypes()[0].getName())){
                                m.invoke(object, ((Boolean)map.get(setFieldName)) ? 1 : 0);
                            }
                        } else {
                            m.invoke(object, map.get(setFieldName));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        }
        return object;
    }

    public static boolean notEmpty(Collection<?> co) {
        return !isEmpty(co);
    }

    public static boolean notEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * parseInt no exception
     */
    public static int parseInt(Object obj) {
        return parseInt(obj, 0);
    }

    public static int parseInt(Object obj, int defaultVal) {
        if (obj == null || isBlank(obj.toString())) {
            return defaultVal;
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
        }
        return defaultVal;
    }

    public static Map<String, Object> putParam(String keys, Object... values) {
        Map<String, Object> param = new HashMap<String, Object>();
        String[] keySet = keys.split(",");
        int valueIndex = 0;
        for (String key : keySet) {
            Object o = values[valueIndex];
            if (o != null && StringUtils.isNotBlank(o.toString())) {
                param.put(key.trim(), values[valueIndex]);
            }
            valueIndex++;
        }
        return param;
    }

    public static Long randomCount(int begin, int end) {
        return Math.round(Math.random() * end + begin);
    }

    public static int randomMilliseconds(int maxMilliseconds) {
        return new Random().nextInt(maxMilliseconds);
    }

    public static String resumeXSS(String s) {
        s = s.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        s = s.replaceAll("&#40;", "\\(").replaceAll("&#41;", "\\)");
        s = s.replaceAll("&#39;", "'");
        s = s.replaceAll("\"\"", "[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']");
        s = s.replaceAll("&#115;cript", "script");
        s = s.replaceAll("&#34;", "\"");
        return s;
    }

    public static void setCookie(HttpServletResponse response, String name, String value) {
        Integer expireTime = DEFAULT_COOKIE_TIME;
        setCookie(response, name, value, expireTime);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, Integer expireTime) {
        if (isBlank(name) || null == expireTime) {
            return;
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expireTime);
        response.addCookie(cookie);
    }

    public static int[] strArrayToIntArray(String[] strs) {
        int[] ints = new int[strs.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(strs[i]);
        }
        return ints;
    }

    public static Integer[] strArrayToIntegerArray(String[] strs) {
        Integer[] ints = new Integer[strs.length];
        for (Integer i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(strs[i]);
        }
        return ints;
    }

    public static String toEscapeSign(String str) {
        return str.replace("+", "{plus}").replace("&", "{and}");
    }

    public static String toOriginalSign(String str) {
        return str.replace("{plus}", "+").replace("{and}", "&");
    }

    /**
     * toString no exception
     */
    public static String toString(Object obj, String defaultVal) {
        if (obj == null || isBlank(obj.toString())) {
            return defaultVal;
        }
        return obj.toString();
    }

    public static Map<Object, List<Map<String, Object>>> listToMap(List<Map<String, Object>> list, String groupByKey) {
        Map<Object, List<Map<String, Object>>> results = new HashMap<Object, List<Map<String, Object>>>();
        for (Map<String, Object> map : list) {
            Object groupBy = BaseUtils.getIntValueFromMap(map, groupByKey);
            if (results.get(groupBy) != null) {
                results.get(groupBy).add(map);
            } else {
                List<Map<String, Object>> innerList = new ArrayList<Map<String, Object>>();
                innerList.add(map);
                results.put(groupBy, innerList);
            }
        }
        return results;
    }


//    public static Map<Object, List<Map<String, Object>>> listToLinkedHashMap(List<Map<String, Object>> list, String groupByKey) {
//        Map<Object, List<Map<String, Object>>> results = new LinkedHashMap<Object, List<Map<String, Object>>>();
//        for (Map<String, Object> map : list) {
//            Object groupBy = BaseUtils.getStringValueFromMap(map, groupByKey);
//            if (results.get(groupBy) != null) {
//                results.get(groupBy).add(map);
//            } else {
//                List<Map<String, Object>> innerList = new ArrayList<Map<String, Object>>();
//                innerList.add(map);
//                results.put(groupBy, innerList);
//            }
//        }
//        return results;
//    }


    public static <T> Map<Object, List<T>> listToLinkedHashMap(List<T> list, String groupByKey){
        Map<Object, List<T>> results = Maps.newLinkedHashMap();
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        for (T t : list) {
            Object groupBy;
            try {
                groupBy = propertyUtilsBean.getNestedProperty(t, groupByKey).toString();
            } catch (Exception e) {
                e.printStackTrace();
                groupBy = "";
            }
            if (results.get(groupBy) != null) {
                results.get(groupBy).add(t);
            } else {
                List<T> innerList = Lists.newArrayList();
                innerList.add(t);
                results.put(groupBy, innerList);
            }
        }
        return results;
    }


    public static String getTopDomain(String url) {
        Pattern p = Pattern.compile("(?<=http://|.)[^.]*?.(com.cn|com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

}
