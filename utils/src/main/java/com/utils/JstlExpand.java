package com.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JstlExpand {
    private final static Logger log = LoggerFactory.getLogger(JstlExpand.class);

    public static Boolean objectInArray(Object obj, Object[] objArr) {
        if (obj == null || objArr == null || objArr.length == 0) {
            return false;
        }
        for (Object o : objArr) {
            if (o.toString().equals(obj.toString())) {
                return true;
            }
        }
        return false;
    }

    public static String replaceAll(String source, String regex, String value) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        return source.replaceAll(regex, value);
    }

    public static String appendParam(String url, String param, String value) throws MalformedURLException, UnsupportedEncodingException {
        URLParser urlParser = new URLParser(url,null);
        String paramVal = urlParser.getParam(param);
        if(StringUtils.isBlank(paramVal)) {
            urlParser.updateParams(param, value);
        }else {
            List<String> values = new ArrayList<String>(Arrays.asList(StringUtils.split(paramVal, ",")));
            values.add(value);
            urlParser.updateParams(param,StringUtils.join(values,','));
        }
        return urlParser.getPath()+"?"+urlParser.createQueryString();
    }

    public static String removeParam(String url, String param, String value) throws MalformedURLException, UnsupportedEncodingException {
        URLParser urlParser = new URLParser(url,null);
        String paramVal = urlParser.getParam(param);
        if(StringUtils.isNotBlank(paramVal)) {
            List<String> values = new ArrayList<String>(Arrays.asList(StringUtils.split(paramVal, ",")));
            values.remove(value);
            urlParser.updateParams(param,StringUtils.join(values,','));
        }
        return urlParser.getPath()+"?"+urlParser.createQueryString();
    }

    public static String replaceParam(String url, String param, String value) throws MalformedURLException, UnsupportedEncodingException {
        URLParser urlParser = new URLParser(url,null);
        urlParser.removeParams(param);
        if(StringUtils.isNotBlank(value)) {
            urlParser.addParam(param,value);
        }
        return urlParser.getPath()+"?"+urlParser.createQueryString();
    }


    public static String replaceUri(String url, String tagCategory, String value, Map<Object, List<Map<String, Object>>> haveTag){
        url = replaceUrlParam(url, "pageNo", "");
        String[] urlArr = url.split("\\?");
        String qs = urlArr.length > 1 ? urlArr[1] : "";
        if(haveTag!= null && haveTag.containsKey(tagCategory)){
            String tagId = ((Map)haveTag.get(tagCategory).get(0)).get("tag_id").toString();
            log.debug("{} change to {}", tagId, value);
            return urlArr[0].replaceAll("/" + tagId + "/", "/" + value + "/") + (StringUtils.isBlank(qs) ? "" : ("?" + qs));
        }else{
            if(urlArr[0].endsWith("/")){
                return urlArr[0] + value + "/" + (StringUtils.isBlank(qs) ? "" : ("?" + qs));
            }
            return urlArr[0] + "/" + value + "/" + (StringUtils.isBlank(qs) ? "" : ("?" + qs));
        }
    }

    public static boolean containsReg(String str, String reg){
        if (str == null) {
            return false;
        }
        if(reg == null) {
            return true;
        }
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static String replaceUrlParam(String url, String paramName, String value) {
        String[] urlArr = url.split("\\?");
        String qs = urlArr.length > 1 ? urlArr[1] : "";
        if (StringUtils.isBlank(qs)) {
            if (StringUtils.isNotBlank(value)) {
                return urlArr[0] + "?" + paramName + "=" + value;
            }
            return urlArr[0];
        }
        String[] qsArr = qs.split("&");
        List<String> qsList = new ArrayList<String>();
        boolean hit = false;
        for (String q : qsArr) {
            if (StringUtils.isBlank(q)) {
                continue;
            }
            String[] qsKV = q.split("=");
            if (StringUtils.equals(qsKV[0], paramName)) {
                hit = true;
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                qsList.add(qsKV[0] + "=" + value);
            } else {
                qsList.add(q);
            }
        }
        if (!hit && StringUtils.isNotBlank(value)) {// 没有原来url里边没有这个参数，加上
            qsList.add(paramName + "=" + value);
        }

        if (qsList.isEmpty()) {
            return urlArr[0];
        }
        return urlArr[0] + "?" + StringUtils.join(qsList, "&");
    }

    public static String truncateStr(String origin,String endStr,int len){
        if(StringUtils.isNotEmpty(origin)){
            if(origin.length() > len){
                return origin.substring(0,len-1)+endStr;
            }else {
                return origin;
            }
        }else {
            return "";
        }
    }

}

