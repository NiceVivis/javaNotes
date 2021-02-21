package com.test.redis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangwei
 * @date 2021/1/8 5:35 下午
 */
@RestController
public class ClusterRedisController {

    private static final Logger logger = LoggerFactory.getLogger(TestRedisController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/test_cluster")
    public void testCluster(){
        stringRedisTemplate.opsForValue().set("ling","666");
        System.out.println(stringRedisTemplate.opsForValue().get("ling"));
    }
}
