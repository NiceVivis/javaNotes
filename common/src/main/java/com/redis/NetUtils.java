package com.redis;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class NetUtils {

    public static final String LOCALHOST = "127.0.0.1";

    public static final String ANYHOST = "0.0.0.0";

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^\\d{1,3}(\\.\\d{1,3}){3}\\:\\d{1,5}$");

    private static String localIp;

    private static String localIp10;

    public static boolean isValidAddress(String address){
        return ADDRESS_PATTERN.matcher(address).matches();
    }

    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");

    public static boolean isLocalHost(String host) {
        return host != null
                && (LOCAL_IP_PATTERN.matcher(host).matches()
                || host.equalsIgnoreCase("localhost"));
    }

    public static boolean isAnyHost(String host) {
        return "0.0.0.0".equals(host);
    }

    protected static boolean isIntraIP(String ip) {
        if(ip == null) {
            return false;
        }

        return ip.startsWith("10.")
                || ip.startsWith("192.168")// 以10开头的，为内网ip
                || ip.startsWith("172.");


    }

    /**
     * 获取有效的本机ip
     * @return
     */
    public static String getValidLocalIP() {
        if(localIp != null) {
            return localIp;
        }
        List<String> ips = getAllIps();
        if(ips.size() == 0) {
            localIp = LOCALHOST;
            return localIp;
        }

        for(String ip : ips) {
            if((!isIntraIP(ip))) {
                // 如果不是内网ip，先忽略
                continue;
            }
            localIp = ip;
            return localIp;
        }
        localIp = ips.get(0);

        return localIp;
    }

    /**
     * 获取内网IP, 10.开头的优先
     * @return
     */
    public static String getValidLocalIP10First() {
        if(localIp10 != null) {
            return localIp10;
        }
        List<String> ips = getAllIps();
        if(ips.size() == 0) {
            localIp10 = LOCALHOST;
            return localIp10;
        }

        List<String> locals = new ArrayList<String>();

        for (String ip : ips) {
            if (isIntraIP(ip)) {
                locals.add(ip);
            }
        }

        // 没有内网IP就返回外网的
        if (locals.size() == 0) {
            localIp10 = ips.get(0);
            return localIp10;
        }

        for (String local : locals) {
            if (local.startsWith("10.")) {
                localIp10 = local;
                return localIp10;
            }
        }
        localIp10 = locals.get(0);
        return localIp10;
    }

    public static List<String> getAllIps() {
        List<NetworkInterface> networkInterfaces = new ArrayList<NetworkInterface>();

        List<String> ips = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements()) {
                NetworkInterface i = e.nextElement();
                networkInterfaces.add(i);
            }

            Collections.sort(networkInterfaces, new Comparator<NetworkInterface>() {
                public int compare(NetworkInterface o1, NetworkInterface o2) {
                    if(o1.getName() == null || o2.getName() == null) {
                        return 0;
                    }
                    return o1.getName().compareTo(o2.getName());
                }
            });

            for(NetworkInterface i : networkInterfaces) {
                Enumeration<InetAddress> eii = i.getInetAddresses();
                while(eii.hasMoreElements()) {
                    InetAddress address = eii.nextElement();
                    String ip = address.getHostAddress();
                    if(ip.indexOf(":") == -1 && !isInvalidLocalHost(ip)) {
                        ips.add(ip);
                    }
                }
            }
        } catch (SocketException e1) {
        }

        return ips;
    }

    public static String getRequestParamsMap(String url, Map<String, Object> map) {
        String path = url;
        try {
            if (StringUtils.isEmpty(url)) {
                return path;
            }
            int index = url.indexOf("?");
            if (index == -1) {
                return path;
            }
            path = url.substring(0, index);
            if (index == url.length() - 1) {
                return path;
            }
            String params = url.substring(index + 1);
            String[] segs = params.split("&");
            for (String seg : segs) {
                String[] kv = seg.split("=", 2);
                if (kv == null || kv.length < 1) {
                    continue;
                }
                map.put(kv[0], kv.length < 2 ? "" : kv[1]);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return path;
    }

    public static boolean isInvalidLocalHost(String host) {
        return host == null
                || host.length() == 0
                || host.equalsIgnoreCase("localhost")
                || host.equals("0.0.0.0")
                || (LOCAL_IP_PATTERN.matcher(host).matches());
    }

    public static boolean isValidLocalHost(String host) {
        return ! isInvalidLocalHost(host);
    }

    public static void main(String[] args) {
//        System.out.println(getValidLocalIP10First());
//        System.out.println(getAllIps());
        Map<String, Object> map = Maps.newHashMap();
        String url = "https://www.baidu.com/s?wd=%E4%B8%AD%E5%9B%BD%E4%BD%A0%E5%A5%BD&rsv_spt=1&rsv_iqid=0x8ff42947000134b6&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=14&rsv_sug1=8&rsv_sug7=100&rsv_sug2=0&inputT=3154&rsv_sug4=3155";
        String path = getRequestParamsMap(url, map);
        System.out.println("path:" + path);
        System.out.println("map:" + map);
    }
}
