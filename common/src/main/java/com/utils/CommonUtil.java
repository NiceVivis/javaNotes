package com.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);
    public static Pattern spmPattern = Pattern.compile("spm=\\d+[\\.]\\d+[\\.]\\d+[\\.]\\d+");
    public static Pattern urlPattern = Pattern.compile("\\S?url=\\S?\\S");
    public static Pattern urlPattern1 = Pattern.compile("\\S?url=\\S");
    public static Integer zero = 0;
    public static Integer one = 1;

    public CommonUtil() {
    }

    public static boolean intTOBoolean(Integer num) {
        if (num == null) {
            return false;
        } else {
            return num.equals(one);
        }
    }

    public static Integer booleanToInt(Boolean flag) {
        if (flag == null) {
            return zero;
        } else {
            return flag ? one : zero;
        }
    }

    public static <T> T conventObj(Object object, Class<T> desClass) {
        if (object == null) {
            return null;
        } else {
            try {
                T obj = desClass.newInstance();
                BeanUtils.copyProperties(object, obj);
                return obj;
            } catch (Exception var4) {
                log.error(var4.getMessage(), var4);
                return null;
            }
        }
    }

    public static String replaceSpm(String mUrl, String newSpm) {
        if (StringUtils.isEmpty(newSpm)) {
            return mUrl;
        } else {
            Matcher matcher = spmPattern.matcher(mUrl);
            String spm = "spm=" + newSpm;
            if (matcher.find()) {
                String group = matcher.group();
                mUrl = matcher.replaceAll(spm);
            } else if (mUrl.indexOf("?") > 0) {
                mUrl = mUrl + "&" + spm;
            } else {
                mUrl = mUrl + "?" + spm;
            }

            return mUrl;
        }
    }

    public static String urlAddSpm(String url, String spm) {
        Matcher matcher = urlPattern.matcher(url);
        if (matcher.find()) {
            return url + "&spm=" + spm;
        } else {
            Matcher matcher1 = urlPattern1.matcher(url);
            if (matcher1.find()) {
                return url + "?spm=" + spm;
            } else {
                return url.indexOf("?") > 0 ? url + "&spm=" + spm : url + "?spm=" + spm;
            }
        }
    }

    public static <T> Map<Long, List<T>> listToMapLong(List<T> list, String attribute) {
        Map<Long, List<T>> map = new HashMap<>();
        list.forEach((e) -> {
            Long key = (Long)getValueByAttribute(e, attribute);
            if (key != null) {
                List<T> eList = map.get(key) == null ? new ArrayList<>(): (List)map.get(key);
                ((List)eList).add(e);
                map.put(key, eList);
            }

        });
        return map;
    }

    public static <T> Map<Integer, List<T>> listToMapInteger(List<T> list, String attribute) {
        Map<Integer, List<T>> map = new HashMap<>();
        list.forEach((e) -> {
            Integer key = (Integer)getValueByAttribute(e, attribute);
            if (key != null) {
                List<T> eList = map.get(key) == null ? new ArrayList<>() : (List)map.get(key);
                ((List)eList).add(e);
                map.put(key, eList);
            }

        });
        return map;
    }

    public static <T> T getValueByAttribute(Object obj, String attribute) {
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();

        for(int i = 0; i < fields.length; ++i) {
            try {
                Field field = fields[i];
                field.setAccessible(true);
                String name = field.getName();
                if (name.equals(attribute)) {
                    Object value = field.get(obj);
                    return (T) value;
                }
            } catch (IllegalAccessException var8) {
                log.error(var8.getMessage(), var8);
            }
        }

        return null;
    }

    public static <T, K> List<T> copyList(List<K> list, Class<T> desClass) {
        List<T> copyList = new ArrayList<>();
        if (list != null && list.size() != 0) {
            list.forEach((e) -> {
                T t = conventObj(e, desClass);
                copyList.add(t);
            });
            return copyList;
        } else {
            return copyList;
        }
    }
}

