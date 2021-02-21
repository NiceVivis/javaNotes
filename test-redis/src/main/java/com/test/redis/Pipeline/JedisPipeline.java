package com.test.redis.Pipeline;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * @author yangwei
 * @date 2021/1/21 11:10 上午
 */
public class JedisPipeline {

    /**
     * redis的管道批量命令
     * @param args
     */
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

        Pipeline p1 = jedis.pipelined();
        for (int i = 0;i<10;i++){
            p1.incr("pipelineKey");
            p1.set("key"+i,"hua");
        }

        List<Object> results = p1.syncAndReturnAll();
        System.out.println(results);
    }
}
