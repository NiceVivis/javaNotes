package com.redis;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;
import java.util.Map;

public class RedisLock extends RedisRepository {
    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 加分布式锁
     * @param lockKey     key
     * @param requestId   唯一的标识(比如可以用uuid),用来实现自己加的锁只有自己才能解锁,防止别人误解锁
     * @param expireTime  锁过期时间,单位:秒
     * @return            加锁成功返回true, 失败返回false
     */
    public boolean lock(final String lockKey, final String requestId, final int expireTime) {
        if (StringUtil.isEmpty(lockKey) || StringUtil.isEmpty(requestId) || expireTime <= 0) {
            return false;
        }
        return getRedisTemplate().execute((RedisCallback<Boolean>) connection -> {
            Object nativeConnection = connection.getNativeConnection();
            Map<String, Object> extparams = Maps.newHashMap();
            String exceptionStr = null;
            Boolean success = false;
            try {
                extparams.put("expire", expireTime);
                extparams.put("key", lockKey);
                extparams.put("method", "lock");
                extparams.put("requestId", requestId);
                CacheTraceHandler.getInstance().pre(null);

                String result = null;
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    result = ((JedisCluster) nativeConnection).set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
                }
                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    result = ((Jedis) nativeConnection).set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
                }
                if (LOCK_SUCCESS.equals(result)) {
                    success = true;
                    return true;
                }
                else {
                    return false;
                }
            }
            catch (Exception e) {
                exceptionStr = ExceptionUtils.getStackTrace(e);
                logger.error(e.getMessage(), e);
                return false;
            }
            finally {
                logCacheRequest(extparams, Collections.singletonMap("success", success), exceptionStr);
            }
        });
    }

    /**
     * 解锁
     * @param lockKey     key
     * @param requestId   唯一的标识(比如可以用uuid),用来实现自己加的锁只有自己才能解锁,防止别人误解锁
     * @return            解锁成功返回true, 失败返回false
     */
    public boolean unlock(final String lockKey, final String requestId) {
        if (StringUtil.isEmpty(lockKey) || StringUtil.isEmpty(requestId)) {
            return false;
        }
        final String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        return getRedisTemplate().execute((RedisCallback<Boolean>) connection -> {
            Object nativeConnection = connection.getNativeConnection();
            Map<String, Object> params = Maps.newHashMap();
            String exceptionStr = null;
            Boolean success = false;
            try {
                params.put("key", lockKey);
                params.put("method", "unLock");
                params.put("requestId", requestId);
                CacheTraceHandler.getInstance().pre(null);

                Object result = null;
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    result = ((JedisCluster) nativeConnection).eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
                }
                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    result = ((Jedis) nativeConnection).eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
                }
                if (RELEASE_SUCCESS.equals(result)) {
                    success = true;
                    return true;
                }
                else {
                    return false;
                }
            }
            catch (Exception e) {
                exceptionStr = ExceptionUtils.getStackTrace(e);
                logger.error(e.getMessage(), e);
                return false;
            }
            finally {
                logCacheRequest(params, Collections.singletonMap("success", success), exceptionStr);
            }
        });
    }
}
