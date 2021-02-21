package com.redis;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;;
import static com.redis.AntiSensitiveUtil.antiSensitiveValue;
import static com.redis.Constant.*;

/**
 * 记录跟踪信息
 * @author hongwm
 */
public class Tracer {

    private static Tracer instance = new Tracer();

    private LogtraceThreadLocal<Map<String, Object>> traceInfos = new LogtraceThreadLocal<Map<String, Object>>();

    public static ThreadLocal<Map<String, Object>> requestTL = new ThreadLocal<>();

    public static ThreadLocal<Map<String, Object>> responseTL = new ThreadLocal<>();

    private static final int TRACEMAP_MAXSIZE = 2000;

    public static final int HTTP_PARAMS_MAXSIZE = 100 * 1000;

    public static final int HTTP_BODY_MAXSIZE = 100 * 1000;

//    private long startTime = System.currentTimeMillis();

//    private long lastSubReqTime = 0;

    private Tracer(){}

    public static Tracer getInstance() {
        return instance;
    }

    /**
     * 对trace信息做重新初始化
     * 重新初始化
     */
    public void reInit() {
        getTraceMap().clear();
        getTraceMap().put(SUB_ID, new AtomicInteger(0));
        setStartTime();
    }

    // 获取当前的traceId
    public String getTraceId() {
        Map<String, Object> traceMap = getTraceMap();
        String traceId = (String) traceMap.get(TraceConstants.TRACE_ID);
        if(traceId == null) {
            traceId = generateTraceId();
            traceMap.put(TraceConstants.TRACE_ID, traceId);
        }
        return traceId;
    }

    public Integer getStressSampingRate() {
        return (Integer) getTraceMap().get(STRESS_SAMPLING_RATE_HEADER);
    }

    public Boolean getRestAlreadyLog() {
        return getTraceMap().containsKey(REST_ALREADY_LOG);
    }

    public void setRestAlreadyLog() {
        getTraceMap().put(REST_ALREADY_LOG, true);
    }

    public void setHttpStatus(int status) {
        getTraceMap().put(TraceConstants.HTTP_STATUS, status);
    }

    public int getHttpStatus() {
        Integer status = (Integer) getTraceMap().get(TraceConstants.HTTP_STATUS);
        if(status == null) {
            status = 200;
        }
        return status;
    }

    public void setTraceId(String traceId) {
        if (StringUtils.isBlank(traceId)) {
            traceId = generateTraceId();
        }
        getTraceMap().put(TraceConstants.TRACE_ID, traceId);
    }

    public void setPath(String path) {
        if (StringUtils.isNotBlank(path)) {
            getTraceMap().put(TraceConstants.REQ_PATH, path);
        }
    }

    public void setErrorType(LogErrorType errorType) {
        if (errorType == null) {
            return;
        }
        getTraceMap().put(TraceConstants.FAULT, errorType.getValue());
    }

    public Integer getErrorType() {
        return (Integer) getTraceMap().get(TraceConstants.FAULT);
    }

    public void setCost(long cost) {
        getTraceMap().put(TraceConstants.COSTS, cost);
    }

    public String getPath() {
        return (String) getTraceMap().get(TraceConstants.REQ_PATH);
    }

    public Object getProperty(String key) {
        return getTraceMap().get(key);
    }

    public void setProperty(String key, Object value) {
        if (StringUtils.isEmpty(key) || null == value) {
            return;
        }
        getTraceMap().put(key, value);
    }

    public void setCapp(String capp) {
        if (StringUtils.isNotBlank(capp)) {
            getTraceMap().put(TraceConstants.CLIENT_APP_NAME, capp);
        }
    }

    public String getCapp() { return (String)getTraceMap().get(TraceConstants.CLIENT_APP_NAME); }

    public void setApp(String app) {
        if (StringUtils.isNotBlank(app)) {
            getTraceMap().put(TraceConstants.APP_NAME, app);
        }
    }

    /**
     * 获取下一个子请求的id
     */
    public Integer genNextSubRequestId() {
        AtomicInteger subIdAI = getCurrentSubRequestId();
        Integer subId = subIdAI.incrementAndGet();
        // 该线程自己的subId,该值不和其他兄弟线程共享,不会被其他线程改变,
        // 而subId是AtomicInteger的,是所有兄弟线程共享的
        getTraceMap().put(OWN_SUB_ID, subId);
        getTraceMap().put(OWN_START_TIME, System.currentTimeMillis());

        // 记录产生下一个子请求的时间
//        lastSubReqTime = System.currentTimeMillis();
//        setSubRequestIdTime(subId);

        return subId;
    }

    @SuppressWarnings("unchecked")
//    public long getSubRequestSeqCosts(String subReqSeq) {
//        Map<String, Long> subSeqStartTimes = (Map<String, Long>) getTraceMap().get("s_times");
//        if (subSeqStartTimes == null) {
//            return -2;
//        }
//        Long time = subSeqStartTimes.get("s-" + subReqSeq);
//        if (time == null) {
//            return -3;
//        }
//        return System.currentTimeMillis() - time;
//    }

    public long getExecuteTime() {
        Long ownStartTime = (Long) getTraceMap().get(OWN_START_TIME);
        if (ownStartTime == null) {
            return System.currentTimeMillis();
        }
        return ownStartTime;
    }


    @SuppressWarnings("unchecked")
//    public void setSubRequestIdTime(int subId) {
//        Map<String, Long> subSeqStartTimes = (Map<String, Long>) getTraceMap().get("s_times");
//        if(subSeqStartTimes == null) {
//            subSeqStartTimes = new HashMap<String, Long>();
//            getTraceMap().put("s_times", subSeqStartTimes);
//        }
//        if(subSeqStartTimes.size() >= TRACEMAP_MAXSIZE) {
//            subSeqStartTimes.clear();
//        }
//
//        String subReqSeq = getPReqSeq() + (subId == 0 ? "" : ("." + subId));
//        subSeqStartTimes.put("s-" + subReqSeq, System.currentTimeMillis());
//    }

    /**
     * 获取最近一个子请求的id,返回的是所有兄弟线程共享的
     * @return
     */
    private AtomicInteger getCurrentSubRequestId() {
        /**
         * 按道理, 正常情况下, 这里不会出现subId不在map中的情况
         * 即使有, 应该是类似这种情况: 比如spring定时器中,以及其他各种各样没有调用SoucheTraceUtil.cleanPre()的情况
         */
        Object subId = getTraceMap().get(SUB_ID);
        if (subId == null) {
            AtomicInteger iv = new AtomicInteger(0);
            getTraceMap().put(SUB_ID, iv);
            return iv;
        }
        else {
            return (AtomicInteger) subId;
        }
    }

    /**
     * 获取该线程自己对应的subId
     * @return
     */
    public Integer getCurrentOwnSubRequestId() {
        Integer ownSubId = (Integer) getTraceMap().get(OWN_SUB_ID);
        return ownSubId == null ? 0 : ownSubId;
    }

    public void setReqSeq(String reqSeq) {
        getTraceMap().put(TraceConstants.REQ_SEQ, reqSeq);
    }

    public void setRid(String rid) {
        getTraceMap().put(TraceConstants.REQ_ID, rid);
    }

    public void setType(String type) {
        if (StringUtils.isNotBlank(type)) {
            getTraceMap().put(TraceConstants.REQ_TYPE, type);
        }
    }

    /**
     * 请求的时间戳,不是记录日志的时间戳
     * @param time
     */
    public void setTime(long time) {
        if (time > 0) {
            String dateStr = DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss.SSS");
            getTraceMap().put(TraceConstants.REQ_TIME, dateStr);
        }
    }

    public String getType() {
        return (String) getTraceMap().get(TraceConstants.REQ_TYPE);
    }

    public void setException(String exception, Long warningCost, Long cost) {
        if (StringUtils.isNotBlank(exception)) {
            getTraceMap().put(TraceConstants.EXCEPTION, exception);
            getTraceMap().put(TraceConstants.ERROR_TAG, 1);
        }
        if (warningCost != null && cost != null && cost >= warningCost) {
            getTraceMap().put(TraceConstants.ERROR_TAG, 1);
        }
    }


    public Integer getErrorTag() {

        return (Integer) getTraceMap().get(TraceConstants.ERROR_TAG);
    }

    /**
     * 获取下一次请求序列号
     * @return
     */
    public String getNextSubReqSeq() {
        return getPReqSeq() + "." + genNextSubRequestId();
    }

    /**
     * 获取当前的序列号
     * @return
     */
    public String getCurSubReqSeq() {
        Integer currentSubRequestId = getCurrentOwnSubRequestId();
        return getPReqSeq() + (currentSubRequestId == 0 ? "" : ("." + currentSubRequestId));
    }

    public String getAppName() {
        return ApplicationInfo.NAME;
    }

    public String getReqId() {
        return getTraceId() + "-" + getReqSeq();
    }

    /**
     * 获取请求序列
     * @return
     */
    public String getReqSeq() {
        String seq = (String) getTraceMap().get(TraceConstants.REQ_SEQ);
        if(seq == null) {
            seq = "1";
        }
        return seq;
    }

    /**
     * 获取父请求序列
     * @return
     */
    public String getPReqSeq() {
        String pseq = (String) getTraceMap().get(TraceConstants.P_REQ_SEQ);
        if (StringUtils.isBlank(pseq)) {
            pseq = "1";
        }
        return pseq;
    }

    public void setPReqSeq(String seq) {
        if (StringUtils.isBlank(seq)) {
            seq = "1";
        }
        getTraceMap().put(TraceConstants.P_REQ_SEQ, seq);
    }

    /**
     * dubbo retry时,该请求对应的第一次try的序列号
     * @param parentSeq
     */
    public void setParentSeq(String parentSeq) {
        if (StringUtils.isNotBlank(parentSeq)) {
            getTraceMap().put(TraceConstants.PARENT_REQ_SEQ, parentSeq);
        }
    }

    // get client ip
    public String getClientIp() {
        return (String) getTraceMap().get(TraceConstants.CLIENT_IP);
    }

    // get server ip
    public String getServerIp() {
        return (String) getTraceMap().get(TraceConstants.SERVER_IP);
    }

//    public long getReqTime() {
//        return getStartTime();
//    }

    /**
     * 获取请求header
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getHeaders() {
        return (Map<String, Object>) getTraceMap().get(TraceConstants.REQ_HEADER);
    }

    /**
     * 获取请求参数信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getRequestParams() {
        return (Map<String, Object>) getTraceMap().get(TraceConstants.REQ_PARAMS);
    }

    /**
     * 得到请求耗时(ms)
     * @return
     */
    public long getCostMills() {
        return System.currentTimeMillis() - getStartTime();
    }

    private void setStartTime() {
        getTraceMap().put(START_TIME, System.currentTimeMillis());
    }

    public long getStartTime() {
        Long startTime = (Long) getTraceMap().get(START_TIME);
        if(startTime == null) {
            setStartTime();
            return System.currentTimeMillis();
        }

        return startTime;
    }

    public long getSubCostMills() {
        Long ownStartTime = (Long) getTraceMap().get(OWN_START_TIME);
        if (ownStartTime == null) {
            return -4;
        }
        return System.currentTimeMillis() - ownStartTime;
    }

    public String getToken() {
        return (String) getTraceMap().get(TraceConstants.TOKEN);
    }

    public void setUserId(String userId) {
        if(userId != null) {
            getTraceMap().put(TraceConstants.USER_ID, userId);
        }
    }

    public String getUserId() {
        return (String) getTraceMap().get(TraceConstants.USER_ID);
    }



    /**
     * 得到最近一次子请求的请求耗时(ms)
     * @return
     */
    @Deprecated
    public long getLastSubRequestCostMills() {
//        if(lastSubReqTime == 0 || getCurrentSubRequestId() == 0) {
//            return 0;
//        }
        return 0;
    }

    public void setHttpRequest(HttpServletRequest request) {
//        getTraceMap().put(TraceConstants.HTTP_REQUEST, request);

        if(request != null) {
            setType(TraceType.TYPE_REST);
//            setupTraceId(request);
            setupReqPath(request);
            setupExtParams(request);
//            setupReqSeq(request);
            setHttpMethod(request.getMethod());
            setupReqHeader(request);
            setupClientIP(request);
            setupServerIP(request);
            setupToken(request);
            setupCapp(request);
        }
    }

    /**
     * 只设置traceid,seq,capp
     * @param request
     */
    public void setBasicFromHttpRequest(HttpServletRequest request) {
//        getTraceMap().put(TraceConstants.HTTP_REQUEST, request);

        if (request != null) {
            setupTraceId(request);
            setupReqSeq(request);
            setupCapp(request);
        }
    }

    public void setBasicFromParams(String traceId, String reqSeq) {
        setTraceId(traceId);
        if(reqSeq == null) {
            reqSeq = "1";
        }
        setReqSeq(reqSeq);
    }

    public void setUserProperties(Properties properties){
        if (properties == null) {
            return;
        }
        Object traceId = properties.get(TraceConstants.TRACE_ID);
        this.setTraceId(traceId == null ? null : traceId.toString());

        Object reqSeq = properties.get(TraceConstants.REQ_SEQ);
        this.setPReqSeq(reqSeq == null ? null : reqSeq.toString() + ".1");

        Object capp = properties.get(TraceConstants.CLIENT_APP_NAME);
        if (capp != null) {
            this.setCapp(capp.toString());
        }
    }

    public void setReqParams(Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            getTraceMap().put(TraceConstants.REQ_PARAMS, params);
        }
    }

    // 填充请求path路径
    private void setupReqPath(HttpServletRequest request) {
        Object attribute = request.getAttribute(TraceConstants.HTTP_REST_PATH);
        String path;
        String concretePath = request.getRequestURI();
        if(attribute != null && attribute instanceof String) {
            path = (String)attribute;
        } else {
            path = concretePath;
        }
        Map<String, Object> traceMap = getTraceMap();
        traceMap.put(TraceConstants.REQ_PATH, path);
        traceMap.put(TraceConstants.REST_CONCRETE_PATH, concretePath);

    }

    private void setupTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(TraceConstants.TRACE_ID);
        setTraceId(traceId);
    }

    private void setupCapp(HttpServletRequest request) {
        String capp = request.getHeader(TraceConstants.CLIENT_APP_NAME);
        if (capp != null) {
            setCapp(capp);
        }
    }

    /**
     * 设置是否是压力测试
     * @param stressTest
     */
    protected void setupStressTest(boolean stressTest) {
        getTraceMap().put(STRESS_TEST_HEADER, stressTest);
    }

    /**
     * 设置采样率
     * @param stressSamplingRate
     */
    public void setStressSamplingRate(int stressSamplingRate) {
        if (stressSamplingRate >= 0 && stressSamplingRate <= 100) {
            getTraceMap().put(STRESS_SAMPLING_RATE_HEADER, stressSamplingRate);
        }
    }

    /**
     * 获取是否是压力测试
     * @return
     */
    protected Boolean getStressTest() {
        Boolean stressTest = (Boolean) getTraceMap().get(STRESS_TEST_HEADER);
        if (stressTest == null) {
            return false;
        }
        return stressTest;
    }

    // 充填请求序列号
    private void setupReqSeq(HttpServletRequest request) {
        String reqSeq = request.getHeader(TraceConstants.REQ_SEQ);
        if(reqSeq == null) {
            reqSeq = "1";
        }
        else {
            reqSeq += ".1";
        }
        getTraceMap().put(TraceConstants.P_REQ_SEQ, reqSeq);
    }

    // 充填请求头
    private void setupReqHeader(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        String userAgent = request.getHeader("user-agent");

        if(referer != null) {
            getTraceMap().put(TraceConstants.REFERER_URL, referer);
        }
        if(userAgent != null) {
            getTraceMap().put(TraceConstants.USER_AGENT, userAgent);
        }
    }

    public String getReferer() {
        return (String) getTraceMap().get(TraceConstants.REFERER_URL);
    }

    public String getUserAgent() {
        return (String) getTraceMap().get(TraceConstants.USER_AGENT);
    }


    public void setClientIp(String clientIp) {
        if (StringUtils.isNotBlank(clientIp)) {
            getTraceMap().put(TraceConstants.CLIENT_IP, clientIp);
        }
    }

    public void setServerIp(String serverIp) {
        if (StringUtils.isNotBlank(serverIp)) {
            getTraceMap().put(TraceConstants.SERVER_IP, serverIp);
        }
    }

    // 充填clientip
    private void setupClientIP(HttpServletRequest request) {
        String ip = UrlUtil.getIpAddr(request);
        if(ip != null) {
            getTraceMap().put(TraceConstants.CLIENT_IP, ip);
        }
    }

    // 充填server ip
    private void setupServerIP(HttpServletRequest request) {
        getTraceMap().put(TraceConstants.SERVER_IP, NetUtils.getValidLocalIP10First());
    }

    public void setExtparams(Map<String, Object> extparams) {
        if (extparams != null && !extparams.isEmpty()) {
            getTraceMap().put(TraceConstants.EXT_PARAMS, extparams);
        }
    }

    public Map<String, Object> getExtparams() {
        return (Map<String, Object>) getTraceMap().get(TraceConstants.EXT_PARAMS);
    }

    public void setServiceApp(String serviceApp) {
        if (StringUtils.isNotBlank(serviceApp)) {
            getTraceMap().put(TraceConstants.SERVICE_APP, serviceApp);
        }
    }
    //https://jira.souche-inc.com/browse/OPTRACE-49
    public void setMqParentRid(String mqParentRid){
        if(StringUtils.isNotBlank(mqParentRid)){
            getTraceMap().put(TraceConstants.MQ_PARENTRID,mqParentRid);
        }else {
            getTraceMap().put(TraceConstants.MQ_PARENTRID,"No Rid");
        }
    }
    public String getMqParentRid(){
        return (String)getTraceMap().get(TraceConstants.MQ_PARENTRID);
    }

    public void setHeader(Map<String, String> header) {
        if (null != header) {
            getTraceMap().put(TraceConstants.REQ_HEADER, header);
        }
    }

    public void setRetLen(Integer len) {
        if (len == null) {
            len = 0;
        }
        getTraceMap().put(TraceConstants.RET_LEN, len);
    }

    public void setHttpMethod(String httpMethod) {
        if (null != httpMethod) {
            getTraceMap().put(TraceConstants.HTTP_METHOD, httpMethod);
        }
    }

    public void setupToken(HttpServletRequest request) {
        String token = antiSensitiveValue(getToken(request));
        if(token != null) {
            getTraceMap().put(TraceConstants.TOKEN, token);
        }
    }

    // 填充请求参数信息
    protected void setupExtParams(HttpServletRequest request) {
        setExtparams(getRequestParams(request));
    }

    protected Map<String, Object> getRequestParams(HttpServletRequest request) {
        Map<String, Object> reqMap =
                new HashMap<String, Object>();

        @SuppressWarnings("unchecked")
        Enumeration<String> paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()) {
            String k = paramNames.nextElement();
            String value = request.getParameter(k);
            if (StringUtils.length(value) >  HTTP_PARAMS_MAXSIZE) {
                value = value.substring(0, HTTP_PARAMS_MAXSIZE);
            }

            reqMap.put(k, value);
        }
        return reqMap;
    }

    public Map<String, Object> getTraceMap() {
        Map<String, Object> traceMap = traceInfos.get();

        if(traceMap == null) {
            traceMap = new ConcurrentHashMap<String, Object>();
            traceInfos.set(traceMap);
        }

        return traceMap;
    }

    protected String generateTraceId() {
        return System.currentTimeMillis() + "_" + genuuid();
    }

    private String getUserFromToken(String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        int startIdx = token.indexOf('_');
        if(startIdx == -1) {
            return null;
        }

        return token.substring(startIdx + 1);
    }

    private String getToken(HttpServletRequest request){
        //Long max = 0l;
        String token = request.getParameter("_security_token");
        if(StringUtils.isEmpty(token)){
            token = request.getHeader("_security_token");
        }
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader("TT");
        }
        if(!StringUtils.isEmpty(token)){
            return token;
        }
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            //可能会找到多个_security_token,取最大的那个
            for(Cookie cookie : cookies){
                if(!cookie.getName().equals("_security_token")){
                    continue;
                }
                return cookie.getValue();
            }
        }
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("token");
        }
        return token;
    }

    protected String genuuid() {
        UUID uuid = UUID.randomUUID();

        // 改变uuid的生成规则
        return HashUtil.convertToHashStr(uuid.getMostSignificantBits(), 2)
                + HashUtil.convertToHashStr(uuid.getLeastSignificantBits(), 2);

    }
}

