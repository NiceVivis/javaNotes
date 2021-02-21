package com.test.redis.lock;

import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author yangwei
 * @date 2021/1/11 3:01 下午
 */
@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private Redisson redisson;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/deduct_stock")
    public String deductStock(){
        String lockey = "product_001";

        String clientId = UUID.randomUUID().toString();

        try {
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockey, "hua"); //jedis.setnx(key,value)

            //设置超时时间,
            // 超时时间设置过小，可能导致该线程还没执行完，锁已经被释放。下一个线程获取到锁，执行代码的时候。
            // 上一个线程执行到finally代码，把当前线程的锁删除。下一个线程能获取到锁，可以通过设置UUID解决，谁加的锁谁释放
            //时间设置太大，下个线程获取锁时间过长
            stringRedisTemplate.expire(lockey,10, TimeUnit.SECONDS);

            //获取锁和设置超时时间放在一起，防止获取锁后，设置超时时间之前出现异常
            //stringRedisTemplate.opsForValue().setIfAbsent(lockey,"hua",clientId,10,TimeUnit.SECONDS);

            //返回false，未获取到锁
            if (!result) {
                return "1001";
            }
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.printf("扣减成功，剩余库存:" + realStock + "");
            } else {
                System.out.printf("扣减失败，库存不足");
            }

        }finally {
            //谁加的锁谁释放
            if (clientId.equals(stringRedisTemplate.opsForValue().get(lockey))) {
                stringRedisTemplate.delete(lockey);
            }
        }

        return "end";
    }

    public String deductStockRedisson(){
        String lockey = "product_001";

        //String clientId = UUID.randomUUID().toString();
        RLock redissionLock = redisson.getLock(lockey);
        try {
            //Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockey, "hua"); //jedis.setnx(key,value)

            //设置超时时间,
            // 超时时间设置过小，可能导致该线程还没执行完，锁已经被释放。下一个线程获取到锁，执行代码的时候。
            // 上一个线程执行到finally代码，把当前线程的锁删除。下一个线程能获取到锁，可以通过设置UUID解决，谁加的锁谁释放
            //时间设置太大，下个线程获取锁时间过长
            stringRedisTemplate.expire(lockey,10, TimeUnit.SECONDS);

            //获取锁和设置超时时间放在一起，防止获取锁后，设置超时时间之前出现异常
            //stringRedisTemplate.opsForValue().setIfAbsent(lockey,"hua",clientId,10,TimeUnit.SECONDS);

            //返回false，未获取到锁
            //if (!result) {
            //    return "1001";
            //}

            //加锁，实现锁续命
            redissionLock.lock();

            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.printf("扣减成功，剩余库存:" + realStock + "");
            } else {
                System.out.printf("扣减失败，库存不足");
            }

        }finally {

            redissionLock.unlock();

//            //谁加的锁谁释放
//            if (clientId.equals(stringRedisTemplate.opsForValue().get(lockey))) {
//                stringRedisTemplate.delete(lockey);
//            }
        }

        return "end";
    }

}
