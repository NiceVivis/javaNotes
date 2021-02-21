package com.redis;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 脱敏工具类，对敏感字段进行脱敏
 */
public class AntiSensitiveUtil {
    private static StringTriedUnit sensitiveKeywords;
    static {
        sensitiveKeywords = new StringTriedUnit();
        sensitiveKeywords.addString("card");        // 卡号关键字
        sensitiveKeywords.addString("pwd");         // 密码关键字
        sensitiveKeywords.addString("password");    // 密码
        sensitiveKeywords.addString("phone");       // 电话关键字
        sensitiveKeywords.addString("username");    // 用户名关键字
        sensitiveKeywords.addString("id_number");   // 身份证号
        sensitiveKeywords.addString("idnumber");    // 身份证号
        sensitiveKeywords.addString("token");       // token
    }

    /**
     * 添加脱敏关键字
     * @param keyword
     */
    public static void addSensitiveKeywords(String keyword) {
        sensitiveKeywords.addString(keyword);
    }

    /**
     * 根据key名，判断是否可能敏感信息
     * @param keyword
     * @return
     */
    public static boolean isSensitiveKeyword(String keyword) {
        return sensitiveKeywords.beMatched(keyword);
    }

    /**
     * 对map中的信息进行脱敏
     * @param map
     */
    public static void antiSensitiveMap(Map<String, Object> map) {
        if(map == null) {
            return;
        }

        for(String key : map.keySet()) {
            if(isSensitiveKeyword(key)) {
                Object value = map.get(key);
                if(value != null && value instanceof Map) {
                    // value是map的话，递归map进行脱敏
                    antiSensitiveMap(map);
                    continue;
                }
                // 如果是需要脱敏的字段，对value进行脱敏
                map.put(key, antiSensitiveValue(String.valueOf(value)));
            }
        }
    }

    /**
     * 对输入的值进行脱敏，如137****4173  35058********24573
     * @param value
     * @return
     */
    public static String antiSensitiveValue(String value) {

        if(StringUtils.isEmpty(value) || value.length() < 2) {
            return value;
        }

        int len = value.length();
        int toAntiSenNum = len / 2 - 1;
        if(toAntiSenNum == 0) {
            toAntiSenNum = 1;
        }
        int antiSensitiveStartPos = toAntiSenNum / 2 + 1;
        int antiSensitiveEndPos = antiSensitiveStartPos + toAntiSenNum;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<value.length(); i++) {
            if(i >= antiSensitiveStartPos && i < antiSensitiveEndPos) {
                sb.append('*');
            } else {
                sb.append(value.charAt(i));
            }
        }


        return sb.toString();

    }

    /**
     * 根据输入的key，对输入的value数据进行脱敏。如果key是需要脱敏的字段，则进行脱敏操作
     * @param key 字段的key值
     * @param value
     * @return
     */
    public static String judgeAndAntiSensitiveValue(String key, String value) {
        if(!isSensitiveKeyword(key)) {
            return value;
        }

        return antiSensitiveValue(value);
    }
}

