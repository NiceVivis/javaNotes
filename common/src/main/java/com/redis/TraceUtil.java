package com.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;

import static com.redis.Constant.logKeys;

/**
 * trace跟踪信息util类
 */
public class TraceUtil {
    /** 默认的超时时间设置 **/
    public static long DEFAULT_TIME_OUT_MILSECS = 8000;

    private static String currentClsName = TraceUtil.class.getName();

    /** 日志的版本号 **/
    private static int LOG_VERSION = 1;

    public static ExecutorService cutTraceExecutor = Executors.newFixedThreadPool(10);

    /**
     * 跟踪信息初始化
     */
    public static void init() {
        Tracer.getInstance().reInit();
    }

    /**
     * 设置http请求的信息
     * @param request
     */
    public static void setupRequestInfo(HttpServletRequest request) {
        Tracer.getInstance().setHttpRequest(request);
    }

    public static void logStacks() {
        Tracer tracer = Tracer.getInstance();
        String appName = tracer.getAppName();
        String ip = NetUtils.getValidLocalIP10First();

        List<ThreadInfo> threads = ThreadInfo.getAllThreadInfos();
        StringBuilder sb = new StringBuilder();
        for(ThreadInfo thread : threads) {
            sb.append('\n');
            sb.append(thread.toString());
        }

        JSONObject json = new JSONObject(true);
        setKVToMap(json, TraceConstants.APP_NAME, appName);
        setKVToMap(json, TraceConstants.SERVER_IP, ip);
        setKVToMap(json, "pid", ApplicationInfo.PID);
        setKVToMap(json, "up_time", ManagementFactory.getRuntimeMXBean().getUptime()/1000);
        setKVToMap(json, TraceConstants.REQ_TIME, System.currentTimeMillis());
        setKVToMap(json, TraceConstants.REQ_TYPE, "stacks");
        setKVToMap(json, "stacks", sb.toString());

        Logger logger = LoggerFactory.getLogger("stacks");
        logger.info("{}", jsonToString(json));
    }

    public static TracerLog newSoucheTracerLog() {
        Tracer tracer = Tracer.getInstance();

        String traceId = tracer.getTraceId();
        String reqSeq = tracer.getReqSeq();
        String reqId = traceId + "-" + reqSeq;
        String appName = tracer.getAppName();
        String clientIp = tracer.getClientIp();
        String serverIp = tracer.getServerIp();

        TracerLog soucheTracerLog = new TracerLog();
        soucheTracerLog.setTraceId(traceId);
        soucheTracerLog.setReqSeq(reqSeq);
        soucheTracerLog.setReqId(reqId);
        soucheTracerLog.setAppName(appName);
        soucheTracerLog.setClientIp(clientIp);
        soucheTracerLog.setServerIp(serverIp);
        soucheTracerLog.setCapp(tracer.getCapp());

        return soucheTracerLog;
    }

    public static void clearSoucheTracerLog() {
        Tracer.getInstance().setCapp("");
        Tracer.getInstance().setClientIp(NetUtils.getValidLocalIP10First());
        Tracer.getInstance().setServerIp("");
    }

    /**
     * 设置用户Properties的信息
     * @param params
     */
    public static void setupRequestProps(Properties params){
        Tracer.getInstance().setUserProperties(params);
    }

    /**
     * 设置trace的自定义属性
     * @param key
     * @param value
     */
    public static void setTraceProperty(String key, Object value) {
        Tracer.getInstance().setProperty(key, value);
    }

    /**
     * 获取自定义属性对应的值
     * @param key
     * @return
     */
    public static Object getTraceProperty(String key) {
        return Tracer.getInstance().getProperty(key);
    }

    public static void setReqType(String type) {
        Tracer.getInstance().setType(type);
    }

    /**
     * 生成并获取下一次子请求的请求序列号
     * @return
     */
    public static String genNextSubSeq() {
        return Tracer.getInstance().getNextSubReqSeq();
    }

    /**
     * 获取当次子请求的请求序列号
     * @return
     */
    public static String getCurrentSubSeq() {
        return Tracer.getInstance().getCurSubReqSeq();
    }

//    private static String logRequest(SoucheLogParams logParams) {
//        // 是否要忽略,比如压力测试时需要采样记录,可能就忽略本条日志了
//        if (checkIfNeedIgnoreLog()) {
//            return "";
//        }
//        if (logParams == null) {
//            logParams = new SoucheLogParams();
//        }
//        String type = logParams.getType();
//        Map<String, Object> params = logParams.getParams();
//        long timeoutWarning = logParams.getTimeoutWarning();
//        boolean isExternal = logParams.isExternal();
//        SoucheTracerLog soucheTracerLog = logParams.getSoucheTracerLog();
//        String exceptionStr = logParams.getExceptionStr();
//        SoucheLogErrorType fault = logParams.getFault();
//        if (params == null) {
//            params = new HashMap<String, Object>();
//        }
//
//        AntiSensitiveUtil.antiSensitiveMap(params);
//
//        SoucheTracer tracer = SoucheTracer.getInstance();
//
//        String reqSeq = soucheTracerLog == null ? getCurrentSubSeq() : soucheTracerLog.getReqSeq();
//        String appName = soucheTracerLog == null ? tracer.getAppName() : soucheTracerLog.getAppName();
//        String clientIp = soucheTracerLog == null ? tracer.getClientIp() : soucheTracerLog.getClientIp();
//        String serverIp = soucheTracerLog == null ? tracer.getServerIp() : soucheTracerLog.getServerIp();
//        String capp = "";
//        if (!isExternal) {
//            capp = soucheTracerLog == null ? tracer.getCapp() : soucheTracerLog.getCapp();
//        }
//        // String reqSeq = tracer.getReqSeq();
//        if(clientIp == null) {
//            clientIp = NetUtils.getValidLocalIP10First();
//        }
//        // if (serverIp == null) {
//        // serverIp = NetUtils.getValidLocalIP10First();
//        // }
//        String traceId = soucheTracerLog == null ? tracer.getTraceId() : soucheTracerLog.getTraceId();
//        String reqId;
//        String ridErrSep = StringUtils.isBlank(exceptionStr)?"":"-e";
//
//        if (soucheTracerLog == null) {
//            reqId = tracer.getTraceId() + "-" + reqSeq + ridErrSep + (isExternal ? "-o" : "");
//        }
//        else {
//            reqId = soucheTracerLog.getReqId() + ridErrSep;
//        }
//
//        JSONObject json = new JSONObject(true);
//        setKVToMap(json, SoucheTraceConstants.REQ_ID, reqId);
//        setKVToMap(json, SoucheTraceConstants.TRACE_ID, traceId);
//        setKVToMap(json, SoucheTraceConstants.REQ_SEQ, reqSeq);
//        setKVToMap(json, SoucheTraceConstants.APP_NAME, appName);
//        setKVToMap(json, SoucheTraceConstants.REQ_TIME, System.currentTimeMillis());
//        setKVToMap(json, SoucheTraceConstants.REQ_TYPE, type);
//        setKVToMap(json, SoucheTraceConstants.CLIENT_APP_NAME, capp);
//        setKVToMap(json, SoucheTraceConstants.LOG_VERSION, LOG_VERSION);
//
//        if (SoucheTraceType.TYPE_REST.equals(type)) {
//            setKVToMap(json, SoucheTraceConstants.TOKEN, AntiSensitiveUtil.antiSensitiveValue(tracer.getToken()));
//            setKVToMap(json, SoucheTraceConstants.HTTP_STATUS, tracer.getHttpStatus());
//            setKVToMap(json, SoucheTraceConstants.USER_ID, tracer.getUserId());
//            setKVToMap(json, SoucheTraceConstants.USER_AGENT, tracer.getUserAgent());
//            setKVToMap(json, SoucheTraceConstants.SIMPLE_TRACE_ID, convertToSimpleTraceId(traceId));
//            setKVToMap(json, SoucheTraceConstants.REFERER_URL, tracer.getReferer());
//
//            Map<String, Object> requestParams = tracer.getRequestParams();
//            AntiSensitiveUtil.antiSensitiveMap(requestParams);
//            setKVToMap(json, SoucheTraceConstants.REQ_PARAMS, requestParams);
//        }
//
//        if(params.containsKey(SoucheTraceConstants.PARENT_REQ_SEQ)) {
//            setKVToMap(json, SoucheTraceConstants.PARENT_REQ_SEQ, params.get(SoucheTraceConstants.PARENT_REQ_SEQ));
//            params.remove(SoucheTraceConstants.PARENT_REQ_SEQ);
//        }
//        if(params.containsKey(SoucheTraceConstants.REQ_PARAMS)) {
//            setKVToMap(json, SoucheTraceConstants.REQ_PARAMS, params.get(SoucheTraceConstants.REQ_PARAMS));
//            params.remove(SoucheTraceConstants.REQ_PARAMS);
//        }
//        if (params.containsKey(SoucheTraceConstants.SERVICE_APP)) {
//            setKVToMap(json, SoucheTraceConstants.SERVICE_APP, params.get(SoucheTraceConstants.SERVICE_APP));
//            params.remove(SoucheTraceConstants.SERVICE_APP);
//        }
//
//        if (soucheTracerLog != null && StringUtils.isNotBlank(soucheTracerLog.getPath())) {
//            setKVToMap(json, SoucheTraceConstants.REQ_PATH, soucheTracerLog.getPath());
//        }
//        else if (StringUtils.isNotEmpty(tracer.getPath())) {
//            setKVToMap(json, SoucheTraceConstants.REQ_PATH, tracer.getPath());
//        }
//        else if(params.containsKey(SoucheTraceConstants.REQ_PATH)) {
//            setKVToMap(json, SoucheTraceConstants.REQ_PATH, params.get(SoucheTraceConstants.REQ_PATH));
//            params.remove(SoucheTraceConstants.REQ_PATH);
//        }
//
//        if (StringUtils.isNotEmpty(clientIp)) {
//            setKVToMap(json, SoucheTraceConstants.CLIENT_IP, clientIp);
//        }
//        else if (params.containsKey(SoucheTraceConstants.CLIENT_IP)) {
//            setKVToMap(json, SoucheTraceConstants.CLIENT_IP, params.get(SoucheTraceConstants.CLIENT_IP));
//            params.remove(SoucheTraceConstants.CLIENT_IP);
//        }
//
//        if (StringUtils.isNotEmpty(serverIp)) {
//            setKVToMap(json, SoucheTraceConstants.SERVER_IP, serverIp);
//        }
//        else if (params.containsKey(SoucheTraceConstants.SERVER_IP)) {
//            setKVToMap(json, SoucheTraceConstants.SERVER_IP, params.get(SoucheTraceConstants.SERVER_IP));
//            params.remove(SoucheTraceConstants.SERVER_IP);
//        }
//
//        if(params.size() > 0) {
//            setKVToMap(json, SoucheTraceConstants.EXT_PARAMS, params);
//        }
//
//        long costs = tracer.getSubRequestSeqCosts(reqSeq);
//
//        if (isExternal) {
//            setKVToMap(json, SoucheTraceConstants.IS_EXTERNAL, true);
//        }
//        else {
//            setKVToMap(json, SoucheTraceConstants.IS_EXTERNAL, false);
//        }
//        setKVToMap(json, SoucheTraceConstants.COSTS, costs);
//
//        EnvEnum envEnum = TracePropertiesConfig.getEnv();
//        if (!envEnum.equals(EnvEnum.PROD)) {
//            setKVToMap(json, SoucheTraceConstants.ENV, envEnum.getEnv());
//        }
//
//        if (params.containsKey(SoucheTraceConstants.WARNING_TIME)) {
//            timeoutWarning = (Integer) params.get(SoucheTraceConstants.WARNING_TIME);
//        }
//
//        setKVToMap(json, SoucheTraceConstants.WARNING_TIME, timeoutWarning);
//
//        LogTypeEnum errType = null;
//
//        // 用Exceptions.fail或fault生成的
//        if (fault != null) {
//            if (fault == SoucheLogErrorType.FAULT) {
//                errType = LogTypeEnum.LOGTYPE_FAULT;
//                setKVToMap(json, SoucheTraceConstants.FAULT, SoucheLogErrorType.FAULT.getValue());
//            }
//            else if (fault == SoucheLogErrorType.FAIL) {
//                setKVToMap(json, SoucheTraceConstants.FAULT, SoucheLogErrorType.FAIL.getValue());
//            }
//        }
//
//        if (StringUtils.isNotBlank(exceptionStr) || costs >= timeoutWarning) {
//            setKVToMap(json, SoucheTraceConstants.ERROR_TAG, 1);
//            if (costs >= timeoutWarning) {
//                setKVToMap(json, SoucheTraceConstants.WARNING, 1);
//            }
//            setKVToMap(json, SoucheTraceConstants.EXCEPTION, exceptionStr);
//            errType = errType == null ? LogTypeEnum.LOGTYPE_ERROR : errType;
//        }
//
//        String uplevelClsName = getUpLevelClsName();
//        Logger logger = LoggerFactory.getLogger(uplevelClsName, errType);
//        logger.info("{}", jsonToString(json));
//
//        return reqId;
//    }

    /**
     * 设置http status
     * @param httpStatus
     */
    public static void setHttpStatus(int httpStatus) {
        Tracer.getInstance().setHttpStatus(httpStatus);
    }

    /**
     * 判断是否要忽略本条日志
     * @return
     */
    private static boolean checkIfNeedIgnoreLog() {
        try {
            // 是否是压力测试
            boolean stressTest = Tracer.getInstance().getStressTest();
            if (!stressTest) {
                return false;
            }
            String tid = Tracer.getInstance().getTraceId();
            return !TraceUtils.needSample(tid, TracePropertiesConfig.getSamplingRate());
        }
        catch (Exception e) {}
        return false;
    }

    /**
     * 打印请求日志
     */
//    public static void logRequest(long warningTime) {
//        // 是否要忽略,比如压力测试时需要采样记录,可能就忽略本条日志了
//        if (checkIfNeedIgnoreLog()) {
//            return;
//        }
//        SoucheTracer tracer = SoucheTracer.getInstance();
//        String appName = tracer.getAppName();
//        String traceId = tracer.getTraceId();
//        String reqSeq = tracer.getReqSeq();
//        String clientIp = tracer.getClientIp();
//        String serverIp = tracer.getServerIp();
//        String token = tracer.getToken();
//        String userId = tracer.getUserId();
//        int httpStatus = tracer.getHttpStatus();
//        String userAgent = tracer.getUserAgent();
//        String referer = tracer.getReferer();
//        String capp = tracer.getCapp();
////        Map<String, Object> headers = tracer.getHeaders();
//        Map<String, Object> requestParams = tracer.getRequestParams();
//
//        AntiSensitiveUtil.antiSensitiveMap(requestParams);
////        AntiSensitiveUtil.antiSensitiveMap(headers);
//
//        long costs = SoucheTracer.getInstance().getCostMills();
//
//        JSONObject json = new JSONObject(true);
//        setKVToMap(json, SoucheTraceConstants.REQ_ID, tracer.getReqId());
//        setKVToMap(json, SoucheTraceConstants.TRACE_ID, traceId);
//        setKVToMap(json, SoucheTraceConstants.SIMPLE_TRACE_ID, convertToSimpleTraceId(traceId));
//        setKVToMap(json, SoucheTraceConstants.REQ_SEQ, reqSeq);
//        setKVToMap(json, SoucheTraceConstants.TOKEN, AntiSensitiveUtil.antiSensitiveValue(token));
//        setKVToMap(json, SoucheTraceConstants.USER_ID, userId);
//        setKVToMap(json, SoucheTraceConstants.REQ_PATH, tracer.getPath());
//        setKVToMap(json, SoucheTraceConstants.APP_NAME, appName);
//        setKVToMap(json, SoucheTraceConstants.CLIENT_IP, clientIp);
//        setKVToMap(json, SoucheTraceConstants.SERVER_IP, serverIp);
//        setKVToMap(json, SoucheTraceConstants.CLIENT_APP_NAME, capp);
//        setKVToMap(json, SoucheTraceConstants.LOG_VERSION, LOG_VERSION);
//        if(userAgent != null) {
//            setKVToMap(json, SoucheTraceConstants.USER_AGENT, userAgent);
//        }
//        if(referer != null) {
//            setKVToMap(json, SoucheTraceConstants.REFERER_URL, referer);
//        }
////        setKVToMap(json, SoucheTraceConstants.REQ_HEADER, headers);
//        setKVToMap(json, SoucheTraceConstants.REQ_PARAMS, requestParams);
//        setKVToMap(json, SoucheTraceConstants.REQ_TIME, tracer.getReqTime());
//        setKVToMap(json, SoucheTraceConstants.REQ_TYPE, tracer.getType());
//        if("rest".equals(tracer.getType())) {
//            setKVToMap(json, SoucheTraceConstants.HTTP_STATUS, httpStatus);
//        }
//
//        setKVToMap(json, SoucheTraceConstants.COSTS, costs);
//
//        EnvEnum envEnum = TracePropertiesConfig.getEnv();
//        if (!envEnum.equals(EnvEnum.PROD)) {
//            setKVToMap(json, SoucheTraceConstants.ENV, envEnum.getEnv());
//        }
//
//        LogTypeEnum errTag = null;
//        if(httpStatus > 200 || costs >= warningTime) {
//            json.put(SoucheTraceConstants.ERROR_TAG, 1);
//            errTag = LogTypeEnum.LOGTYPE_ERROR;
//            if (costs >= warningTime) {
//                json.put(SoucheTraceConstants.WARNING_TIME, warningTime);
//                json.put(SoucheTraceConstants.WARNING, 1);
//            }
//        }
//
//        Logger logger = LoggerFactory.getLogger(getUpLevelClsName(), errTag);
//        logger.info("{}", jsonToString(json));
//    }

    private static void setKVToMap(Map<String, Object> json, String key, Object value) {
        if(value == null) {
            return;
        }

        json.put(key, value);
    }

    protected static String jsonToString(JSONObject json) {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, Object> e : json.entrySet()) {
            if(sb.length() > 0) {
                sb.append('\t');
            }

            Object v = e.getValue();
            if(!(v instanceof String)) {
                try{
                    v = JSON.toJSONString(v);
                } catch(Exception e1) {
                }
            } else {
                v = StringUtils.replaceChars((String) v, '\t', ' ');
            }
            sb.append(e.getKey()).append(":").append(v);
        }

        return sb.toString();
    }

    /**
     * 获取上层调用的类名
     * @return
     */
    public static String getUpLevelClsName() {
        StackTraceElement[] eles = Thread.currentThread().getStackTrace();
        if(eles.length <= 1) {
            return null;
        }

        for(int i=1; i<eles.length; i++) {
            StackTraceElement e = eles[i];
            if(!e.getClassName().equals(currentClsName)) {
                return e.getClassName();
            }
        }
        return eles[eles.length-1].getClassName();
    }

    /**
     * 获取下一次请求的http header信息
     * @return
     */
    public static List<Header> getNextRequestHttpHeader() {
        List<Header> headers = new ArrayList<Header>();
        String traceId = getTraceId();
        //https://jira.souche-inc.com/browse/OPTRACE-28
        //20181017 原本的是在当前新建下一个的seq往后传递。修改为只传递当前的seq，由接收方自己将seq+.1
        //  String nextSubSeq = genNextSubSeq();
        String nextSubSeq = Tracer.getInstance().getReqSeq();
        String userId = Tracer.getInstance().getUserId();
        String capp = PerfCounter.appName;
        if (Tracer.getInstance().getStressTest()) {
            headers.add(new BasicHeader(TraceConstant.STRESS_TEST_HEADER, "stress"));
            Integer rate = Tracer.getInstance().getStressSampingRate();
            if (rate != null && rate >= 0) {
                headers.add(new BasicHeader(TraceConstant.STRESS_SAMPLING_RATE_HEADER, rate.toString()));
            }
        }

        headers.add(new BasicHeader(TraceConstants.TRACE_ID, traceId));
        headers.add(new BasicHeader(TraceConstants.REQ_SEQ, nextSubSeq));
        headers.add(new BasicHeader(TraceConstants.CLIENT_APP_NAME, capp));
        if(userId != null) {
            headers.add(new BasicHeader(TraceConstants.USER_ID, userId));
        }

        return headers;
    }
    //mq-producer 往 mq-consumer传递参数（传入rid）
    public static Properties getNextRequestProps(){
        Properties properties = new Properties();
//        String traceId = getTraceId();
//        String nextSubSeq = genNextSubSeq();
        String capp = PerfCounter.appName;
        if (Tracer.getInstance().getStressTest()) {
            properties.put(TraceConstant.STRESS_TEST_HEADER, "stress");
            Integer rate = Tracer.getInstance().getStressSampingRate();
            if (rate != null && rate >= 0) {
                properties.put(TraceConstant.STRESS_SAMPLING_RATE_HEADER, rate.toString());
            }
        }
//       https://jira.souche-inc.com/browse/OPTRACE-49
//        properties.put(SoucheTraceConstants.TRACE_ID, traceId);
//        properties.put(SoucheTraceConstants.REQ_SEQ, nextSubSeq);
        properties.put(TraceConstants.REQ_ID,Tracer.getInstance().getReqId());
        properties.put(TraceConstants.CLIENT_APP_NAME, capp);

        return properties;
    }

    /**
     * 置压力测试标志位
     * @param stress
     */
    public static void dealStressTest(String stress, String rateStr) {
        if (StringUtils.isNotEmpty(stress)) {
            Tracer.getInstance().setupStressTest(true);
            if (StringUtils.isNotEmpty(rateStr)) {
                try {
                    int rate = Integer.parseInt(rateStr);
                    Tracer.getInstance().setStressSamplingRate(rate);
                }
                catch (Exception e) {}
            }
        }
        else {
            Tracer.getInstance().setupStressTest(false);
        }
    }

    /**
     * 获取trace id
     * @return
     */
    public static String getTraceId() {
        return Tracer.getInstance().getTraceId();
    }

    public static String getSimpleTraceId() {
        return convertToSimpleTraceId(Tracer.getInstance().getTraceId());
    }

    public static String getAppName() {
        return Tracer.getInstance().getAppName();
    }

    /**
     * 获取本次请求的请求序列
     * @return
     */
    public static String getRequestSeq() {
        return Tracer.getInstance().getReqSeq();
    }

    public static String getPReqSeq(String seq){
        if(StringUtils.isNotEmpty(seq) && seq.contains(".")){
            String[] array = seq.split("\\.");
            String parentSeq = "";
            for(int i=0;i<array.length-1;i++){
                parentSeq += array[i] + ".";
            }
            if(parentSeq.length()>1){
                parentSeq = parentSeq.substring(0,parentSeq.length()-1);
            }
            return parentSeq;
        } else {
            return "0";
        }
    }

    public static void logAppTraceInfo(String type, Map<String, Object> params) {
        Logger logger = LoggerFactory.getLogger(getUpLevelClsName());

        AntiSensitiveUtil.antiSensitiveMap(params);

        Tracer tracer = Tracer.getInstance();
        String appName = tracer.getAppName();
        String serverIp = NetUtils.getValidLocalIP10First();
        String traceId = Tracer.getInstance().generateTraceId();
        JSONObject json = new JSONObject(true);
        setKVToMap(json, TraceConstants.TRACE_ID, traceId);
        setKVToMap(json, TraceConstants.REQ_ID, tracer.getReqId());
        setKVToMap(json, TraceConstants.APP_NAME, appName);
        setKVToMap(json, TraceConstants.REQ_TIME, System.currentTimeMillis());
        setKVToMap(json, TraceConstants.REQ_TYPE, type);
        setKVToMap(json, TraceConstants.SERVER_IP, serverIp);
        setKVToMap(json, TraceConstants.REQ_PARAMS, params);

        logger.info("{}", jsonToString(json));
    }

    /**
     * 把traceId转换为6个字节的简化版(1517318571398_nv0o转换为AMnv0o)
     * @param traceId
     * @return 简化版的traceid
     */
    public static String convertToSimpleTraceId(String traceId) {
        if(traceId == null) {
            return traceId;
        }
        try {
            // 把traceId根据'_'切成两块
            String[] tids = traceId.split("_", 2);
            if(tids.length != 2) {
                return traceId;
            }

            return HashUtil.convertToHashStr(Long.parseLong(tids[0]), 2) + tids[1];
        } catch (Throwable e) {
            return traceId;
        }
    }

    public static void printTraceLogByType(String traceType) {
        Class clz = TraceType.typeToClsMap.get(traceType);
        if (null == clz) {
            printTraceLog();
        } else {
            printTraceLog(clz.getName());
        }
    }

    public static void printTraceLog() {
        String upLevelClsName = getUpLevelClsName();
        printTraceLog(upLevelClsName);
    }

    private static void printTraceLog(String upLevelClsName) {
        JSONObject json = new JSONObject(true);
        Map<String, Object> traceMap = Tracer.getInstance().getTraceMap();
        for (String logKey : logKeys) {
            if (traceMap.containsKey(logKey)) {
                //仅mq-consumer打印 MQ_PARENTRID 的信息。
                if(logKey.equals(TraceConstants.MQ_PARENTRID) && traceMap.get(TraceConstants.REQ_TYPE).equals(TraceType.TYPE_MQ_CONSUMER)){
                    setKVToMap(json, logKey, traceMap.get(logKey));
                }else {
                    setKVToMap(json, logKey, traceMap.get(logKey));
                }
            }
        }

        LogTypeEnum logTypeEnum = null;
        Integer errorTag = Tracer.getInstance().getErrorTag();
        if (errorTag != null) {
            logTypeEnum = LogTypeEnum.LOGTYPE_ERROR;

            Integer errorType = Tracer.getInstance().getErrorType();
            if (errorType != null && errorType == LogErrorType.FAULT.getValue()) {
                logTypeEnum = LogTypeEnum.LOGTYPE_FAULT;
                setKVToMap(json, TraceConstants.FAULT, LogErrorType.FAULT.getValue());
            }else if(errorType !=null && errorType ==LogErrorType.FAIL.getValue()){
                setKVToMap(json, TraceConstants.FAULT, LogErrorType.FAIL.getValue());
            }

        }

//        Logger logger = LoggerFactory.getLogger(upLevelClsName, logTypeEnum);
//        logger.info("{}", jsonToString(json));
    }


    /**
     * just for test
     * @return
     */
    public static String printTraceMap() {
        return JSON.toJSONString(Tracer.getInstance().getTraceMap());
    }
}
