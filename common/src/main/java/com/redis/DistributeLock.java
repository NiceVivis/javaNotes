package com.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class DistributeLock implements InitializingBean {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
