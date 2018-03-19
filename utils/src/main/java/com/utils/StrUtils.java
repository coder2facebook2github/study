package com.utils;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrUtils {

    public static Object byte2Object(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            ObjectInputStream inputStream;
            inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object obj = inputStream.readObject();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Set<Integer> extractIntegers(String target) {
        Set<Integer> ret = new HashSet<Integer>();
        String[] idstrs = target.split("[^0-9]+");
        for (String idstr : idstrs) {
            if (StringUtils.isNotBlank(idstr)) {
                try {
                    ret.add(Integer.parseInt(idstr.trim()));
                } catch (Exception e) {
                }
            }
        }
        return ret;
    }

    public static String joinMapColumn(List<Map<String, Object>> list, String key, String joinSymbo) {
        if (list == null || list.size() == 0 || StringUtils.isBlank(key)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> map : list) {
            if (StringUtils.isNotBlank(sb.toString())) {
                sb.append(",");
            }
            sb.append(BaseUtils.getStringValueFromMap(map, key));
        }
        return sb.toString();
    }

    public static byte[] object2Bytes(Object value) {
        if (value == null) {
            return null;
        }
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream;
        try {
            outputStream = new ObjectOutputStream(arrayOutputStream);
            outputStream.writeObject(value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                arrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arrayOutputStream.toByteArray();
    }

    public static String fixLength(String src, int length, char padding, boolean leading) {
        if (src == null) {
            src = "";
        }
        if (length <= src.length()) {
            return src;
        }
        StringBuilder sb = new StringBuilder(src);
        for (int i = src.length(), j = length; i < j; i++) {
            if (leading) {
                sb.insert(0, padding);
            } else {
                sb.append(padding);
            }
        }
        return sb.toString();
    }
}