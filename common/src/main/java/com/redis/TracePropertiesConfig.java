package com.redis;

/**
 * trace全局配置
 */
public class TracePropertiesConfig {
    private static PropertyConfig envConfig;

    private static EnvEnum envEnum = null;

//    private static Integer samplingRate = null;

    static {
        envConfig = new PropertyConfig("/opt/settings/server.properties");
    }

    /**
     * 获取环境
     * @return
     */
    public static EnvEnum getEnv() {
        if (TracePropertiesConfig.envEnum != null) {
            return TracePropertiesConfig.envEnum;
        }
        String env = envConfig.getValue("env");
        if (EnvEnum.PROD.getEnv().equals(env)) {
            TracePropertiesConfig.envEnum =  EnvEnum.PROD;
        }
        else if (EnvEnum.PREPUB.getEnv().equals(env)) {
            TracePropertiesConfig.envEnum = EnvEnum.PREPUB;
        }
        else if (EnvEnum.TEST.getEnv().equals(env)) {
            TracePropertiesConfig.envEnum = EnvEnum.TEST;
        }
        else if (EnvEnum.DEV.getEnv().equals(env)) {
            TracePropertiesConfig.envEnum = EnvEnum.DEV;
        }
        else if (EnvEnum.OTHER.getEnv().equals(env)) {
            TracePropertiesConfig.envEnum = EnvEnum.OTHER;
        }
        else {
            TracePropertiesConfig.envEnum = EnvEnum.PROD;
        }
        return TracePropertiesConfig.envEnum;
    }

    /**
     * 获取采样率,默认是0%
     * @return
     */
    public static int getSamplingRate() {
        Integer rateFromTL = Tracer.getInstance().getStressSampingRate();
        if (rateFromTL != null) {
            return rateFromTL;
        }
        String samplingRate = envConfig.getValue("samplingRate", "0");
        int rateRet = 0;
        try {
            int rate = Integer.parseInt(samplingRate);
            if (rate >= 0 && rate <= 100) {
                rateRet = rate;
            }
        }
        catch (Exception e) {}
        Tracer.getInstance().setStressSamplingRate(rateRet);
        return rateRet;
    }
}

