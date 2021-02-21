package com.redis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class CommonUtil {
    private static Random rand = new Random();
    private final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    private static char CHARACTERS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    /**
     * 对obj里面的Integer、Long、Float、Double类型字段中，为null值的成员，置为0
     */
    public static void invokeDefaultValue(Object obj) {
        ObjectUtil.invokeDefaultValue(obj);
    }

    /**
     * 把obj2里面不为null的字段，更新到toUpdateObj中
     */
    public static void updateObjectValue(Object toUpdateObj, Object obj2) {
        ObjectUtil.updateObjectValue(toUpdateObj, obj2);
    }

    public static Object getObjectFieldValue(Object obj, String field) {
        return ObjectUtil.getObjectFieldValue(obj, field);
    }

    public static List<Entry<String, Object>> getValidFieldValues(Object obj) {
        return ObjectUtil.getValidFieldValues(obj);
    }

    /**
     * 生成object里面所有有效参数的string串
     */
    public static String ObjectToString(Object obj) {
        return ObjectUtil.ObjectToString(obj);
    }

    /**
     * invoke调用obj里面的一个函数
     */
    public static Object invokeMethod(Object obj, String methodName, Object... params) {
        return ObjectUtil.invokeMethod(obj, methodName, params);
    }

    /**
     * 生成n以内的随机数
     */
    public static int randomNum(int n) {
        return Math.abs(rand.nextInt(n) % n);
    }


    public static <K, V> String toJsonString(Map<K, V> obj) {
        return JSON.toJSONString(obj);
    }

    @SuppressWarnings("unchecked")
//    public static Map<String, Object> getJsonMap(String jsonString) {
//        if (jsonString == null) {
//            return null;
//        }
//
//        return JsonUtils.fromJson(jsonString, Map.class);
//    }

    /**
     * 得到int值
     */
    public static int getIntValue(Object obj) {
        try {
            if (obj == null) {
                return 0;
            }

            if (obj instanceof Integer) {
                return (Integer) obj;
            }

            if (obj instanceof Long) {
                return ((Long) obj).intValue();
            }

            if (obj instanceof String) {
                if (StringUtil.isEmpty((String) obj)) {
                    return 0;
                }
                return Integer.parseInt(StringUtil.trim((String) obj));
            }
        } catch (Exception e) {
            // logger.warn("", e);
        }
        return 0;
    }

    public static long getLongValue(Object obj) {
        try {
            if (obj == null) {
                return 0;
            }

            if (obj instanceof Integer) {
                return ((Integer) obj).longValue();
            }

            if (obj instanceof Long) {
                return ((Long) obj).longValue();
            }

            if (obj instanceof String) {
                return Long.parseLong((String) obj);
            }
        } catch (Exception e) {
            logger.warn("", e);
        }
        return 0;
    }

    public static float getFloatValue(Object obj) {
        try {
            if (obj == null) {
                return 0;
            }

            if (obj instanceof Integer) {
                return ((Integer) obj).floatValue();
            }

            if (obj instanceof Long) {
                return ((Long) obj).floatValue();
            }

            if (obj instanceof String) {
                return Float.parseFloat(((String) obj).replaceAll("\\s+", ""));
            }
            if (obj instanceof Float) {
                return ((Float) obj).floatValue();
            }
            if (obj instanceof Double) {
                return ((Double) obj).floatValue();
            }
        } catch (Exception e) {
            logger.warn("", e);
        }
        return 0;
    }

    /**
     * 生成长度为length的随机数
     */
    public static String genRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int randomNum = randomNum(CHARACTERS.length);
            sb.append(CHARACTERS[randomNum]);
        }

        return sb.toString();
    }


    public static InputStream openResourceInputStream(String file) throws IOException {
        if (new File(file).exists()) {
            return new FileInputStream(file);
        }

        URL u = CommonUtil.class.getResource(file);
        if (u == null) {
            u = CommonUtil.class.getResource("/" + file);
            if (u == null) {
                return null;
            }
        }
        return u.openStream();
    }

    public static String getAbsoluteFilePath(String file) {
        if (new File(file).exists()) {
            return file;
        }

        URL u = CommonUtil.class.getResource(file);
        if (u == null) {
            u = CommonUtil.class.getResource("/" + file);
            if (u == null) {
                return file;
            }
        }

        // Fix Issue #23
        try {
            return Paths.get(u.toURI()).toString();
        } catch (URISyntaxException ex) {
            return file;
        }
    }

    public static String getAbsoluteFilePathStream(String file) {
        return getAbsoluteFilePath(file);
    }

    /**
     * 在字符串头部填充字符
     *
     * @param str
     *            要填充的字符串
     * @param num
     *            长度
     * @param c
     *            要填充的字符
     * @return
     */
    public static String fillStringHead(String str, int num, char c) {
        if (str == null) {
            return null;
        }

        if (str.length() >= num) {
            return str;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = str.length(); i < num; i++) {
            sb.append(c);
        }
        sb.append(str);
        return sb.toString();
    }
    public static String getRequestUrl(HttpServletRequest request) {
        return getRequestUrl(request, false);
    }

    public static String getRequestUrl(HttpServletRequest request, boolean fullParam) {
        StringBuffer sb = new StringBuffer();
        sb.append(request.getRequestURI());

        Map<String, String> paramMap = new TreeMap();
        if(fullParam) {
            for(Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                String k = entry.getKey();
                String[] arr = entry.getValue();
                String value = arr != null && arr.length > 0 ? arr[0] : null;
                paramMap.put(k, value);
            }
        } else {
            String query = request.getQueryString();
            if (StringUtil.isNotEmpty(query)) {
                String[] kvs = query.split("&");
                for(String kv : kvs) {
                    String[] arr = kv.split("=", 2);
                    if(arr.length != 2) {
                        continue;
                    }
                    paramMap.put(arr[0], arr[1]);
                }
            }
        }

        int n = 0;
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String k = key.toString().toLowerCase();
            if (n++ == 0) {
                sb.append("?");
            } else {
                sb.append("&");
            }
            String value = entry.getValue();
            // 加上脱敏信息
            //sb.append(key).append("=").append(AntiSensitiveUtil.judgeAndAntiSensitiveValue(k, value));
        }

        return sb.toString();
    }
    public static String getRemoteIpAddress(HttpServletRequest request) {
        String realIP = request.getHeader("X-Real-IP");
        if (StringUtil.isNotEmpty(realIP)) {
            return realIP;
        }

        return request.getRemoteAddr();
    }


    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        //ipAddress = this.getRequest().getRemoteAddr();
        ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1")||ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
