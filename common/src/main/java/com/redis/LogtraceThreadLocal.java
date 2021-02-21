package com.redis;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.concurrent.ConcurrentHashMap;

public class LogtraceThreadLocal<T> extends TransmittableThreadLocal<T> {
    @Override
    protected T childValue (T parentValue) {
        if (parentValue instanceof ConcurrentHashMap) {
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<String, Object>(((ConcurrentHashMap) parentValue).size());
            concurrentHashMap.putAll((ConcurrentHashMap) parentValue);
            return (T) concurrentHashMap;
        }
        return super.childValue(parentValue);
    }

    @Override
    protected T copy (T parentValue) {
        if (parentValue instanceof ConcurrentHashMap) {
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<String, Object>(((ConcurrentHashMap) parentValue).size());
            concurrentHashMap.putAll((ConcurrentHashMap) parentValue);
            return (T) concurrentHashMap;
        }
        return super.copy(parentValue);
    }
}
