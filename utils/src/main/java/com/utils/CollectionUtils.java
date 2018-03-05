package com.utils;

import java.util.*;

/**
 * @author ripflowers
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

    public static int getIntValue(Map<String, Object> map, String key) {
        if (map.containsKey(key) && map.get(key) != null) {
            return (Integer) map.get(key);
        }
        return 0;
    }

    public static String getStringValue(Map<String, Object> map, String key) {
        if (map.get(key) != null) {
            return (String) map.get(key);
        }
        return "";
    }

    public static Date getDateValue(Map<String, Object> map, String key) {
        if (map.get(key) != null) {
            return (Date) map.get(key);
        }
        return null;
    }

    public static boolean getBooleanValue(Map<String, Object> map, String key) {
        if (map.get(key) != null) {
            return ((Boolean) map.get(key)).booleanValue();
        }
        return false;
    }

    public static double getDoubleValue(Map map, String key) {
        if (map.get(key) != null) {
            return (Double) map.get(key);
        }
        return 0.0d;
    }

    public static float getFloatValue(Map map, String key) {
        if (map.get(key) != null) {
            return (Float) map.get(key);
        }
        return 0.0f;
    }

    private static long getLongValue(Map map, String key, long defaultValue) {
        if(map.get(key) != null) {
            return (Long) map.get(key);
        }
        return defaultValue;
    }

    public static long getLongValue(Map map, String key) {
        return getLongValue(map, key, 0l);
    }

    public static List getListValue(Map map, String key) {
        if (map.get(key) != null) {
            return (List) map.get(key);
        }
        return new ArrayList();
    }

    public static <T> List<T> randomSubList(List<T> list, int subSize) {
        List<T> returnValue = new ArrayList<T>();
        Random r = new Random();
        List<T> source = new LinkedList<T>(list);
        for(int i = 0; i < subSize && source.size() > 0; i++) {
            int idx = r.nextInt(source.size());
            returnValue.add(source.get(idx));
            source.remove(idx);
        }
        return returnValue;
    }

    public static <K,V> List<V> getCertifiedValues(List<Map<K, V>> list, K key) {
        List<V> returnValue = new LinkedList<V>();
        if (isEmpty(list)) {
            return returnValue;
        }
        for (Map<K, V> map : list) {
            if (isEmpty(map)) {
                continue;
            }
            V value = map.get(key);
            if (null != value) {
                returnValue.add(value);
            }
        }
        return returnValue;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null) {
            return true;
        }
        return isEmpty(map.keySet());
    }

    public static boolean isEmptyList(List<?> list) {
        return isEmpty(list);
    }


}
