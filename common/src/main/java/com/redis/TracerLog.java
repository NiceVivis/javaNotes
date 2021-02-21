package com.redis;

public class TracerLog {
    private String reqSeq;
    private String appName;
    private String clientIp;
    private String serverIp;
    private String traceId;
    private String reqId;
    private String capp;
    private String path;
    //private long cost;

    public String getReqSeq() {
        return reqSeq;
    }

    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

//    public long getCost() {
//        return cost;
//    }
//
//    public void setCost(long cost) {
//        this.cost = cost;
//    }

    public String getCapp() {
        return capp;
    }

    public void setCapp(String capp) {
        this.capp = capp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

