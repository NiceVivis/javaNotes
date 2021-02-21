package com.redis;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class SystemInfoUtil {
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private static final String STR_HOST_ERROR_DETECTED = "HOST_ERROR_DETECTED";
    private static final String STR_IP_ERROR_DETECTED = "IP_ERROR_DETECTED";

    public static String getHostName() {
        String host = STR_HOST_ERROR_DETECTED;
        try {
            try {
                // 如果能直接取到正确IP就返回，通常windows下可以
                InetAddress localAddress = InetAddress.getLocalHost();
                host = localAddress.getHostName();
            } catch (Throwable e) {
                InetAddress localAddress = getLocalAddress();
                if (localAddress != null) {
                    host = localAddress.getHostName();
                } else {
                    host = STR_HOST_ERROR_DETECTED;
                }
            }
        } catch (Throwable ex) {
            // ignore
        }

        return host;
    }

    public static String getHostIP() {
        String ip = STR_IP_ERROR_DETECTED;

        try {
            if (getLocalAddress() != null) {
                ip = getLocalAddress().getHostAddress();
            } else {
                ip = STR_IP_ERROR_DETECTED;
            }
        } catch (Throwable ex) {
            // ignore
        }

        return ip;
    }

    public static InetAddress getLocalAddress() {
        InetAddress localAddress = null;
        try {
            // 如果能直接取到正确IP就返回，通常windows下可以
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {

        }

        try {
            // 通过轮询网卡接口来获取IP
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null)
                while (interfaces.hasMoreElements())
                    try {
                        NetworkInterface network = (NetworkInterface) interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = (InetAddress) addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e) {

                                }
                            }
                        }
                    } catch (Throwable e) {
                    }
        } catch (Throwable e) {
        }
        return localAddress;
    }

    /**
     * 判断是否为有效合法的外部IP，而非内部回环IP
     *
     * @param address
     * @return
     */
    public static boolean isValidAddress(InetAddress address) {
        if ((address == null) || (address.isLoopbackAddress())) {
            return false;
        }

        String ip = address.getHostAddress();

        return (ip != null) && (!"0.0.0.0".equals(ip)) && (!"127.0.0.1".equals(ip)) && (IP_PATTERN.matcher(ip).matches());
    }

    public static String getRemoteIpAddress(HttpServletRequest request) {
        String realIP = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(realIP)) {
            return realIP;
        }
        return request.getRemoteAddr();
    }
}

