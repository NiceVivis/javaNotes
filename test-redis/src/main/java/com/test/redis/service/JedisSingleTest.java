package com.test.redis.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author yangwei
 * @date 2021/1/8 9:39 上午
 */
public class JedisSingleTest {

    public static void main(String[] args) {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        //timeout,这里既是连接超时又是读写超时，从jedis2.8开始有区分connectionTimeout和soTimeout的构造函数
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"39.106.148.147",6379,3000,"123456");
        Jedis jedis = null;
        //从连接池拿出一个连接执行命令
        jedis = jedisPool.getResource();
        System.out.println(jedis.set("single","hua"));
        System.out.println(jedis.get("single"));
    }
}
