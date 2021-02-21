package com.redis;

/**
 * 环境
 */
public enum EnvEnum {
    PREPUB("prepub"), PROD("prod"), TEST("test"), DEV("dev"), OTHER("other");

    private String env;

    EnvEnum(String env) {
        this.env = env;
    }

    public String getEnv() {
        return this.env;
    }
}