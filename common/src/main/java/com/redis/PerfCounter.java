package com.redis;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PerfCounter{
    // 发送性能计算结果的周期：1分钟

    private static MetricReporter reporter;

    public static String appName;

    public static boolean enable = false;

    private static List<ExecutableMetricMap> matrics = new ArrayList<ExecutableMetricMap>();

    public static void setList(List<ExecutableMetricMap> list) {
        for(ExecutableMetricMap executableMetricMap : list){
            addMetricMapJob(executableMetricMap);
        }
    }

    private static void addMetricMapJob(ExecutableMetricMap matric){
        if(matric == null){
            return;
        }
        boolean find = false;
        for(ExecutableMetricMap executableMetricMap : matrics){
            if(executableMetricMap.getName().equalsIgnoreCase(matric.getName())){
                find = true;
            }
        }
        if(!find){
            matrics.add(matric);
        }
    }

    private static class SendPerfResultTask extends TimerTask {

        @Override
        public void run() {
            try{
                Map<String, Object> map = reporter.getReport();
                TraceUtil.logAppTraceInfo(TraceType.TYPE_JVM, map);
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    static {
        appName = PropertiesUtil.getString("systemInfo.appName", "");
        if(StringUtils.isEmpty(appName)){
            //如果没有不再从dubbo中获取
            // appName = PropertiesUtil.getString("dubbo.application", "");
            appName=System.getProperty("APP_NAME");
        }

        ApplicationInfo.NAME = appName;

        ApplicationInfo.ENV = PropertiesUtil.getString("systemInfo.env", "dev");

        long interval = PropertiesUtil.getLong("monitor.interval", 1 * 60 * 1000L);

        enable = PropertiesUtil.getBoolean("monitor.enable", false);

        long lastTimePoint = (new Date().getTime() / interval) * interval;
        Date firstSendTime = new Date(lastTimePoint + interval);

        reporter = new MetricReporter();

        JVMMetrics jvmMetrices = new JVMMetrics();
        addMetricMapJob(jvmMetrices);

        reporter.setList(matrics);

        if(enable){
            Timer sendPerfResultTaskTimer = new Timer("Perfiler-SendPerfResultTaskThread", true);
            sendPerfResultTaskTimer.scheduleAtFixedRate(new SendPerfResultTask(), firstSendTime, interval);
        }
    }
}

