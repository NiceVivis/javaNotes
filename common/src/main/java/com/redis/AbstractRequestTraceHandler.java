package com.redis;

import java.util.Iterator;
import java.util.Map;

import static com.redis.Constant.coreInfo;

public abstract class AbstractRequestTraceHandler<T extends RequestTraceInfo> extends AbstractBaseTraceHandler<T> {
    @Override
    protected void cleanPre(T t) {
        /**
         * 只保留当前线程对应的关键的几个链路信息,以便后面的兄弟请求可以使用
         * 其余的都是本次请求特有的,要清掉
         */
        Map<String, Object> traceMap = Tracer.getInstance().getTraceMap();
        Iterator<String> it = traceMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (!coreInfo.contains(key)) {
                it.remove();
            }
        }
    }

    @Override
    protected void cleanPost(T t) {

    }

    @Override
    protected void pre(T t) {
        super.pre(t);
        //https://jira.souche-inc.com/browse/OPTRACE-49 将rid的生成放在pre里，便于传入mq-consumer
//        String seq = SoucheTracer.getInstance().getCurSubReqSeq();
        String seq = Tracer.getInstance().getNextSubReqSeq();
        Tracer.getInstance().setReqSeq(seq);
        long time = Tracer.getInstance().getExecuteTime();
        Tracer.getInstance().setTime(time);
        String rid = Tracer.getInstance().getTraceId() + "-" + seq;
        Tracer.getInstance().setRid(rid);
    }

    @Override
    protected void post(T t) {
        super.post(t);
//        String seq = SoucheTracer.getInstance().getCurSubReqSeq();
//        SoucheTracer.getInstance().setReqSeq(seq);
        long cost = Tracer.getInstance().getSubCostMills();
        Tracer.getInstance().setCost(cost);
        Tracer.getInstance().setApp(Tracer.getInstance().getAppName());

        // rid https://jira.souche-inc.com/browse/OPTRACE-52
//        String ridErrSep = StringUtils.isBlank(t.getExceptionStr()) ? "" : "-e";
//        String rid = SoucheTracer.getInstance().getTraceId() + "-" + seq + ridErrSep + "-o";
//        String rid = SoucheTracer.getInstance().getTraceId() + "-" + seq;
//        SoucheTracer.getInstance().setRid(rid);

        Tracer.getInstance().setException(t.getExceptionStr(), t.getWarningCost(), cost);

        Tracer.getInstance().setClientIp(NetUtils.getValidLocalIP10First());

        logInfo(t);
    }

    @Override
    protected void checkPreArgs(T t) {

    }

    @Override
    protected void checkPostArgs(T t) {
        if (t == null) {
            throw new IllegalArgumentException("参数不能为null");
        }
    }
}
