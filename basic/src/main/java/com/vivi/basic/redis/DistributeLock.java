package com.vivi.basic.redis;

import com.redis.RedisLock;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yangwei
 * @date 2021/1/7 2:40 下午
 */
@Component
public class DistributeLock implements InitializingBean {

    @Autowired
    RedisTemplate redisTemplate;

    private RedisLock redisLock;
    @Override
    public void afterPropertiesSet() throws Exception {
        RedisLock redisLock = new RedisLock();
        redisLock.setRedisTemplate(redisTemplate);
        this.redisLock = redisLock;
    }

    public RedisLock getRedisLock(){
        return this.redisLock;
    }
}
