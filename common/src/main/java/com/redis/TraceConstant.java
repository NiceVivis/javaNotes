package com.redis;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.redis.TraceConstants.*;

public class TraceConstant {
    public static final String STRESS_TEST_HEADER = "stressTestMarkForLogtrace";

    public static final String STRESS_SAMPLING_RATE_HEADER = "stressSamplingRateForLogtrace";

    public static final String OWN_START_TIME = "ownStartTime";

    public static final String OWN_SUB_ID = "ownSubId";

    public static final String SUB_ID = "subId";

    public static final String REST_ALREADY_LOG = "restAlreadyLog";

    public static final String START_TIME = "startTime";

    public static Set<String> coreInfo = Sets.newHashSet();

    public static String[] logKeys =  {
            REQ_ID,
            TRACE_ID,
            SIMPLE_TRACE_ID,
            REQ_SEQ,
            PARENT_REQ_SEQ,
            APP_NAME,
            REQ_TIME,
            REQ_TYPE,
            CLIENT_APP_NAME,
            REQ_PATH,
            CLIENT_IP,
            SERVER_IP,
            EXT_PARAMS,
            REQ_PARAMS,
            USER_AGENT,
            TOKEN,
            USER_ID,
            COSTS,
            ERROR_TAG,
            FAULT,
            EXCEPTION,
            REFERER_URL,
            MQ_PARENTRID
    };

    static {
        coreInfo.add(TraceConstants.TRACE_ID);
        coreInfo.add(SUB_ID);
        coreInfo.add(TraceConstants.P_REQ_SEQ);
        coreInfo.add(TraceConstants.APP_NAME);
        coreInfo.add(TraceConstants.ENV);
        coreInfo.add(TraceConstants.LOG_VERSION);
        coreInfo.add(START_TIME);
    }
}
