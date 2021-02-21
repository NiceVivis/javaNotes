package com.redis;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public abstract class RedisRepository {
    private RedisTemplate<String, String> redisTemplate;
    private String keyPrefix;
    protected long opTimeout = (Long)Config.getValue("redis.timeout", Long.class, 3000L);
    protected int timeoutExceptionThreshold = (Integer)Config.getValue("redis.timeout", Integer.class, 3000);
    protected boolean enable = (Boolean)Config.getValue("redis.enable", Boolean.class, true);
    protected int cacheTraceLimitPerRequest = (Integer)Config.getValue("redis.trace_limit", Integer.class, 2000);

    public RedisRepository() {
    }

    protected String buildKey(String key) {
        if (StringUtil.isEmpty(this.keyPrefix)) {
            return key;
        } else {
            String newKey = this.keyPrefix + ":" + key;
            return newKey;
        }
    }

    protected <T> CacheMethodReturnType<T> processCacheRequest(CacheInfo cacheInfo, Function<String, CacheReturnType<T>> func) {
        String type = cacheInfo.getCacheOpsType().getType();
        String key = cacheInfo.getKey();
        Long expire = cacheInfo.getExpire();
        TimeUnit unit = cacheInfo.getUnit();
        Integer start = cacheInfo.getStart();
        Integer end = cacheInfo.getEnd();
        String exceptionStr = null;
        Map<String, Object> extparams = Maps.newHashMap();
        Map params = null;

        try {
            if (!StringUtil.isEmpty(key) && this.enable) {
                String newKey = this.buildKey(key);
                extparams.put("key", newKey);
                extparams.put("method", type);
                if (cacheInfo.getLen() != null) {
                    extparams.put("len", cacheInfo.getLen());
                }

                if (expire != null && unit != null) {
                    extparams.put("expire", "" + expire + " " + unit.toString());
                }

                if (start != null) {
                    extparams.put("start", start);
                }

                if (end != null) {
                    extparams.put("end", end);
                }

                CacheTraceHandler.getInstance().pre((CacheTraceInfo)null);
                CacheReturnType<T> returnType = (CacheReturnType)func.apply(newKey);
                CacheMethodReturnType var14;
                if (!returnType.isWithReturn()) {
                    var14 = CacheMethodReturnType.builder().success(true).result((Object)null).build();
                    return var14;
                } else {
                    params = returnType.getParams();
                    var14 = CacheMethodReturnType.builder().success(true).result(returnType.getResult()).build();
                    return var14;
                }
            } else {
                CacheMethodReturnType var12 = CacheMethodReturnType.builder().build();
                return var12;
            }
        } catch (Throwable var18) {
            exceptionStr = ExceptionUtils.getStackTrace(var18);
            throw var18;
        } finally {
            this.logCacheRequest(extparams, params, exceptionStr);
        }
    }

    protected void logCacheRequest(Map<String, Object> extparams, Map<String, Object> params, String exceptionStr) {
        try {
            CacheTraceInfo info = CacheTraceInfo.builder().extparams(extparams).params(params).build();
            info.setType("redis");
            info.setExceptionStr(exceptionStr);
            info.setWarningCost(this.opTimeout);
            CacheTraceHandler.getInstance().post(info);
        } catch (Throwable var5) {
        }

    }

    public boolean expire(String key, Long timeout) {
        String exceptionStr = null;
        Map<String, Object> params = Maps.newHashMap();
        boolean result = true;

        boolean var6;
        try {
            if (!StringUtil.isEmpty(key)) {
                String newKey = this.buildKey(key);
                params.put("key", newKey);
                params.put("method", "expire");
                CacheTraceHandler.getInstance().pre((CacheTraceInfo)null);
                result = this.getRedisTemplate().expire(newKey, timeout, TimeUnit.SECONDS);
                return result;
            }

            var6 = result;
        } catch (Exception var10) {
            exceptionStr = ExceptionUtils.getStackTrace(var10);
            throw var10;
        } finally {
            this.logCacheRequest(params, (Map)null, exceptionStr);
        }

        return var6;
    }

    public void delete(String key) {
        String exceptionStr = null;
        HashMap params = Maps.newHashMap();

        try {
            if (!StringUtil.isEmpty(key)) {
                String newKey = this.buildKey(key);
                params.put("key", newKey);
                params.put("method", "delete");
                CacheTraceHandler.getInstance().pre((CacheTraceInfo)null);
                this.getRedisTemplate().delete(newKey);
                return;
            }
        } catch (Exception var8) {
            exceptionStr = ExceptionUtils.getStackTrace(var8);
            throw var8;
        } finally {
            this.logCacheRequest(params, (Map)null, exceptionStr);
        }

    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return this.redisTemplate;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}

