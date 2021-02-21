package com.test.redis.service;

import redis.clients.util.JedisClusterCRC16;

/**
 * @author yangwei
 * @date 2021/1/8 9:28 下午
 * 通过hash函数计算槽位
 */
public class CRC16 {

    public static void main(String[] args) {
        String str = "key1";
        System.out.printf(String.valueOf(JedisClusterCRC16.getSlot(str)));
    }
}
