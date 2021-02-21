package com.vivi.basic.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 * @date 2021/1/12 2:20 下午
 */
public class SharelikeRedisson {

    private static final String SHARE_LIKE_ID_KEY = "SHARE_LIKE_ID_KEY_%s";
    private static final Integer LOCK_EXPIRE_TIME_SECONDS = 10;
    private static final Integer LOCK_WAIT_TIME_SECONDS = 6;

    @Autowired
    private RedissonClient redissonClient;

    public Boolean sharelike(ShareLikeDo shareLikeDo){
        //给用户id加锁
        String lockey = String.format(SHARE_LIKE_ID_KEY,shareLikeDo.getUserId());
        RLock lock = redissonClient.getLock(lockey);

        try {
            int waitTime = LOCK_WAIT_TIME_SECONDS * 1000;
            do {
                //lock.lock(LOCK_EXPIRE_TIME_SECONDS,TimeUnit.SECONDS);
                if (lock.tryLock(LOCK_EXPIRE_TIME_SECONDS,TimeUnit.SECONDS)){  //lock.lock可以实现锁续命。lock.tryLock有一个超时时间
                    //获取锁并处理完加锁逻辑后跳出循环
                    break;
                }

            }while ((waitTime -= 100) > 0);

        }catch (Exception e){
            return false;
        }finally {
            lock.unlock(); //释放锁
        }

        return null;
    }

    public void sharelike(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(15,20,5, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy());

        try {
            for (int i = 0;i<=20;i++){
                ShareLikeDo shareLikeDo = new ShareLikeDo();

                threadPoolExecutor.submit(()->{
                    sharelike(shareLikeDo);
                });

            }
        }catch (Exception e){

        }
    }
}
