package com.redis;

/**
 * rachet请求参数信息
 * @author hongwm
 */
public class TraceConstants {
    /** traceid **/
    public final static String TRACE_ID = "_tid";
    /**简化版的traceid**/
    public static final String SIMPLE_TRACE_ID = "_stid";
    /** 请求时间 **/
    public final static String REQ_TIME = "_time";
    /** 当前请求序列 **/
    public final static String REQ_SEQ = "_seq";
    /** 父序列 **/
    public final static String P_REQ_SEQ = "p_seq";

    /** 请求id **/
    public final static String REQ_ID = "_rid";

    /** 客户端ip **/
    public final static String CLIENT_IP = "_cip";

    /** 服务端ip **/
    public final static String SERVER_IP = "_sip";

    /** 服务器请求端的应用名称 **/
    public final static String CLIENT_APP_NAME = "_capp";

    /** app名称 **/
    public final static String APP_NAME = "_app";

    /** 请求的header信息 **/
    public final static String REQ_HEADER = "_header";

    /** 请求类型 **/
    public final static String REQ_TYPE = "_type";

    /** 请求参数 **/
    public final static String REQ_PARAMS = "_params";

    /** 扩展参数 **/
    public final static String EXT_PARAMS = "_ext_params";

    public final static String HTTP_REQUEST = "req";

    /** 请求路径 **/
    public final static String REQ_PATH = "_path";

    /** 异常信息 **/
    public final static String EXCEPTION = "_exception";

    /** 错误信息追踪 **/
    public final static String ERROR = "_error";

    /** 耗时时间 **/
    public final static String COSTS = "_cost";

    /** 是否对外请求 **/
    public final static String IS_EXTERNAL = "_ext";

    /** 返回长度 **/
    public final static String RET_LEN = "_ret_len";
    /**token**/
    public final static String TOKEN = "_tk";
    /**用户id**/
    public final static String USER_ID = "_uid";

    /** http返回状态 **/
    public final static String HTTP_STATUS = "_status";

    /** 错误信息标签 **/
    public final static String ERROR_TAG = "_err_tag";

    /** referer url **/
    public final static String REFERER_URL = "_referer";

    /** user agent **/
    public final static String USER_AGENT = "_ua";

    /** 用户设定的超时报警时间 **/
    public final static String WARNING_TIME = "_warning_time";

    /** 是否报警 **/
    public final static String WARNING = "_warning";

    /** optimusException 是否报警 0:不报 1:报警 **/
    public final static String FAULT = "_fault";

    /** dubbo重试时,该请求对应的第一次try的序列号 **/
    public final static String PARENT_REQ_SEQ="_parent_seq";

    /** 环境:预发,线上,测试等 **/
    public final static String ENV = "_env";

    /** 日志版本号 **/
    public final static String LOG_VERSION = "_version";

    /** 服务端名称 **/
    public final static String SERVICE_APP = "_sapp";

    /** http method : GET, POST, PUT... **/
    public final static String HTTP_METHOD = "_http_method";
    /** mq parentRid **/
    public final static String MQ_PARENTRID= "_mq_parentRid";

    public final static String HTTP_REST_PATH = "com.souche.bumblebee.rest.traceInfo.path";

    public static final String REST_CONCRETE_PATH = "_concrete_path";
}
