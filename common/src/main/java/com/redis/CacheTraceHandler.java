package com.redis;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class CacheTraceHandler<T extends CacheTraceInfo> extends AbstractRequestTraceHandler<T> {
    private static CacheTraceHandler instance = new CacheTraceHandler();

    protected CacheTraceHandler() {}

    public static CacheTraceHandler getInstance() {
        return instance;
    }

    @Override
    public void pre(T t) {
        try {
            super.pre(t);
            //https://jira.souche-inc.com/browse/OPTRACE-112
            // SoucheTraceUtil.genNextSubSeq();
        }
        catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void post(T t) {
        try {
            super.post(t);
        }
        catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void cleanPre(T t) {

    }

    @Override
    protected void logInfo(T t) {
        Tracer.getInstance().setExtparams(t.getExtparams());

        Tracer.getInstance().setReqParams(t.getParams());

        Tracer.getInstance().setType(t.getType());

        TraceUtil.printTraceLog();
    }
}
