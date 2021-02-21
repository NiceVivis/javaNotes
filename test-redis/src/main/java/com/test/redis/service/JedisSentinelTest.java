package com.test.redis.service;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yangwei
 * @date 2021/1/8 10:10 上午
 */
public class JedisSentinelTest {

    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        String masterName = "mymaster";
        Set<String> sentinels = new HashSet<>();
        sentinels.add(new HostAndPort("39.106.148.147",26379).toString());
        sentinels.add(new HostAndPort("39.106.148.147",26380).toString());
        sentinels.add(new HostAndPort("39.106.148.147",26381).toString());

        //JedisSentinelPool其实本质跟jedisPool类似，都是redis主节点建立的连接池
        //JedisSentinelPool并不是说与sentinel建立的连接池，而是通过sentinel发现redis主节点并与其建立连接
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName,sentinels,config,3000,"123456");
        Jedis jedis = null;

        jedis = jedisSentinelPool.getResource();
        System.out.println(jedis.set("sentinel","yang"));
        System.out.println(jedis.get("sentinel"));

        //注意这里不是关闭连接，在jedisPool模式下，jedis会被还给资源池
        if (jedis != null){
            jedis.close();
        }
    }
}
