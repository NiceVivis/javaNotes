package com.test.redis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author yangwei
 * @date 2021/1/7 9:02 下午
 */
@RestController
public class TestRedisController {


    private static final Logger logger = LoggerFactory.getLogger(TestRedisController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试节点挂了哨兵重新选举新的master节点，客户端是否能动态感知到
     * 新的master选举出来后，哨兵会把消息发布出去，客户端实际上是实现了一个消息监听机制，
     * 当哨兵把新master的消息发布出去，客户端会立马感知到新master的信息，从而动态切换访问的master ip
     * @throws InterruptedException
     */
    @RequestMapping("/test_sentinel")
     public void testSentinel() throws InterruptedException {
        int i = 1;
        while (true){
            stringRedisTemplate.opsForValue().set("hua"+i,i+"");  //jedis.set(key,value)
            System.out.printf("设置key: "+"hua"+i);
            i++;
            Thread.sleep(1000);
        }
    }
}
