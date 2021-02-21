package com.redis;

public enum LogTypeEnum {

    LOGTYPE_ERROR("error", "错误"),
    LOGTYPE_FAULT("fault", "故障");

    private LogTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}

