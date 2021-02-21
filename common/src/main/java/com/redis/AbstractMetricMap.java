package com.redis;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractMetricMap implements MetricMap{
    protected static Map<String, Object> guageMap = new ConcurrentHashMap<String, Object>();

    @Override
    public void set(int value, String... keys){
        String key = getkey(keys);
        guageMap.put(key, value);
    }

    @Override
    public void set(long value, String... keys){
        String key = getkey(keys);
        guageMap.put(key, value);
    }

    @Override
    public void set(double value, String... keys){
        String key = getkey(keys);
        guageMap.put(key, value);
    }

    @Override
    public void set(float value, String... keys){
        String key = getkey(keys);
        guageMap.put(key, value);
    }

    @Override
    public void set(boolean value, String... keys){
        int intValue = value?1:0;
        set(intValue,keys);
    }

    public static void put(int value, String... keys){
        String key = getkey(keys);
        guageMap.put(key, value);
    }

    public static void put(long value, String... keys){
        String key = getkey(keys);
        guageMap.put(key, value);
    }

    public static void put(double value, String... keys){
        String key = getkey(keys);
        guageMap.put(key, value);
    }

    public static void put(float value, String... keys){
        String key = getkey(keys);
        guageMap.put(key, value);
    }

    public static void put(boolean value, String... keys){
        String key = getkey(keys);
        guageMap.put(key, value);
    }

    protected static String getkey(String[] keys){
        if(keys == null || keys.length == 0){
            return "";
        }
        return StringUtils.arrayToDelimitedString(keys, ".");
    }
}

