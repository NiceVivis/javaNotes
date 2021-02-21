package com.redis;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 日志跟踪类型
 */
public class TraceType {

    /** dubbo请求类型 **/
    public final static String TYPE_DUBBO = "dubbo-service";

    public final static String TYPE_DUBBO_CONSUMER = "dubbo-consumer";
    /** rest请求类型 **/
    public final static String TYPE_REST = "http-server";

    /** 对外http请求 **/
    public final static String TYPE_HTTP = "http-client";
    /** 访问数据库 **/
    public final static String TYPE_SQL = "sql";
    /** mq类型 **/
    public final static String TYPE_MQ = "mq-producer";

    public final static String TYPE_MQ_CONSUMER = "mq-consumer";

    /** 缓存类型 **/
    public final static String TYPE_CACHED = "cache";

    public final static String TYPE_REDIS = "redis";

    public final static String TYPE_HESSIAN = "hessian-service";

    public final static String TYPE_HESSIAN_CONSUMER = "hessian-consumer";

    public final static String TYPE_OSS = "oss";

    public final static String TYPE_MTS = "mts";

    public final static String TYPE_JVM = "jvm";

    public static List<String> getAll(){
        ArrayList<String> list = new ArrayList<String>();
        list.add(TYPE_DUBBO);
        list.add(TYPE_DUBBO_CONSUMER);
        list.add(TYPE_REST);
        list.add(TYPE_HTTP);
        list.add(TYPE_SQL);
        list.add(TYPE_MQ);
        list.add(TYPE_MQ_CONSUMER);
        list.add(TYPE_CACHED);
        list.add(TYPE_REDIS);
        list.add(TYPE_HESSIAN);
        list.add(TYPE_HESSIAN_CONSUMER);
        list.add(TYPE_OSS);
        list.add(TYPE_MTS);
        list.add(TYPE_JVM);
        return list;
    }

    public static Map<String, Class> typeToClsMap = Maps.newHashMap();

    static {
//        typeToClsMap.put(SoucheTraceType.TYPE_HESSIAN, HessianServiceTraceHandler.class);
//        typeToClsMap.put(SoucheTraceType.TYPE_REST, RestTraceHandler.class);
    }
}
