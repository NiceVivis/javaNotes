package com.test.redis.service;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yangwei
 * @date 2021/1/8 3:52 下午
 */
public class JedisClusterTest {

    public static void main(String[] args) throws IOException {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        Set<HostAndPort> jedisCluesterNode = new HashSet<>();
        jedisCluesterNode.add(new HostAndPort("",8001));
        jedisCluesterNode.add(new HostAndPort("",8002));
        jedisCluesterNode.add(new HostAndPort("",8003));
        jedisCluesterNode.add(new HostAndPort("",8004));
        jedisCluesterNode.add(new HostAndPort("",8005));
        jedisCluesterNode.add(new HostAndPort("",8006));

        JedisCluster jedisCluster = null;
        try {
        // connectionTimeout:指的是连接一个url的连接等待时间
        //soTimeout：指的是连接上一个url，获取response的返回等待时间
            //通过initializeSlotsCache方法来缓存本地数据，每次可以从缓存中获取配置
            jedisCluster = new JedisCluster(jedisCluesterNode);
            //set操作通过
            // connection = connectionHandler.getConnectionFromSlot(JedisClusterCRC16.getSlot(key));
            // return getCRC16(key) & (16384 - 1);  通过hash函数来计算slot
            System.out.println(jedisCluster.set("cluster","chun"));
            System.out.println(jedisCluster.get("cluster"));

            if (jedisCluster != null){
                jedisCluster.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedisCluster.close();
        }
    }
}
