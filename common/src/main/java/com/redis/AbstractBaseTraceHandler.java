package com.redis;

public abstract class AbstractBaseTraceHandler<T extends BaseTraceInfo> {
    protected void pre(T t) {
        checkPreArgs(t);
        cleanPre(t);
    }

    protected void post(T t) {
        checkPostArgs(t);
        cleanPost(t);
    }

    protected abstract void checkPreArgs(T t);

    protected abstract void checkPostArgs(T t);

    protected abstract void cleanPre(T t);

    protected abstract void cleanPost(T t);

    protected abstract void logInfo(T t);
}

