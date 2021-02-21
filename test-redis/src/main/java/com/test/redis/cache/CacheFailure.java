package com.test.redis.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;

import java.util.Random;

/**
 * @author yangwei
 * @date 2021/1/21 9:43 上午
 */
public class CacheFailure {

    /**
    public String get(String key){

        //从缓存中获取数据
        String cacheValue = cache.get(key);
        //缓存为空
        if (StringUtils.isBlank(cacheValue)){
            //从存储中获取
            String storageValue = storage.get(key);
            cache.set(key,storageValue);
            //设置一个过期时间（300到600之间的一个随机数）
            int expireTime = new Random().nextInt(300)+300;
            if (storageValue == null){
                cache.expire(key,expireTime);
            }
            return storageValue;
        }else {
            return cacheValue;
        }
    }
**/
}
