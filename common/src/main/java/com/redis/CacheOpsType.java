package com.redis;

public enum CacheOpsType {
    ADD("add"), GET("get"), TOUCH("touch"), SET("set"), SETNX("setnx"), SETXX("setxx"), DELETE("delete"),
    INCR("incr"), RPUSH("rpush"), LPOP("lpop"), BLPOP("blpop"), LRANGE("lrange"), LPUSH("lpush"),
    LTRIM("ltrim"), RPOP("rpop"), BRPOP("brpop");

    CacheOpsType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    private String type;
}
