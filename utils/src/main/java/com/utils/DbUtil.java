package com.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtil {


    public static String getSingleWhereSql(Map<String, Object> searchinfo) {
        if (searchinfo == null) return "";
        StringBuffer wheresql = new StringBuffer();
        for (Object key : searchinfo.keySet()) {
            Object value = searchinfo.get(key);
            if (value instanceof Integer) {
                if (!Integer.valueOf(value + "").equals(-1))
                    wheresql.append(" and t.").append(key).append("=").append(value);
            } else if (value instanceof Long) {
                if (!Long.valueOf(value + "").equals(-1L))
                    wheresql.append(" and t.").append(key).append("=").append(value);
            } else if (value instanceof Float) {
                if (!Float.valueOf(value + "").equals(-1F))
                    wheresql.append(" and t.").append(key).append("=").append(value);
            } else if (value instanceof String) {
                if (StringUtils.isNotEmpty(value.toString())) {
                    //处理时间
                    if ("starttime".equals(key)) {
                        wheresql.append(" and t.create_time >= '" + value + "'");
                    } else if ("endtime".equals(key)) {
                        wheresql.append(" and t.create_time <= '" + value + "'");
                    } else {
                        wheresql.append(" and t.").append(key).append("=").append(value);
                    }
                }
            } else {

            }
        }
        return wheresql.toString();
    }


    public static HashMap<String, Object> getSingleWhereSqlAndParameter(Map<String, Object> searchinfo) {
        HashMap<String, Object> results = new HashMap<>();
        StringBuffer wheresql = new StringBuffer();
        List<Object> paramtes = new ArrayList<>();
        int i = 0;
        for (Object key : searchinfo.keySet()) {
            Object value = searchinfo.get(key);
            if (value instanceof Integer) {
                if (!Integer.valueOf(value + "").equals(-1)) {
                    wheresql.append(" and t.").append(key).append("= ? ");
                    paramtes.add(value);
                }
            } else if (value instanceof Long) {
                if (!Long.valueOf(value + "").equals(-1L)) {
                    wheresql.append(" and t.").append(key).append("= ? ");
                    paramtes.add(value);
                }
            } else if (value instanceof Float) {
                if (!Float.valueOf(value + "").equals(-1F)) {
                    wheresql.append(" and t.").append(key).append("= ? ");
                    paramtes.add(value);
                }
            } else if (value instanceof String) {
                if (StringUtils.isNotEmpty(value.toString())) {
                    wheresql.append(" and t.").append(key).append("= ? ");
                    paramtes.add(value);
                }
            } else {

            }
        }
        results.put("wheresql", wheresql);
        results.put("parames", paramtes.toArray());
        return results;
    }

}
