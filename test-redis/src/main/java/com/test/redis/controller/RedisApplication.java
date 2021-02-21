package com.test.redis.controller;
/**
 * @author yangwei
 * @date 2021/1/11 5:26 下午
 */

import org.redisson.Redisson;
import org.redisson.config.Config;

public class RedisApplication{

    public static void main(String[] args) {

    }

    public Redisson redisson(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//        config.useClusterServers()
//                .addNodeAddress("")
//                .addNodeAddress("")
//                .addNodeAddress("");
      return (Redisson) Redisson.create(config);

    }
}
