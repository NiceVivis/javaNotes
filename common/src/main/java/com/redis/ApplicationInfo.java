package com.redis;

import java.lang.management.ManagementFactory;

public class ApplicationInfo {
    public static String NAME;
    public static String ENV;
    public static String HOST = SystemInfoUtil.getHostName();
    public static String IP = SystemInfoUtil.getHostIP();
    public static String PID;

    static {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        PID = name.split("@")[0];
    }
}
