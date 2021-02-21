package com.vivi.basic.redis;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 * @date 2021/1/7 2:30 下午
 */

public class ShareLikeCurrent {

    private static final String SHARE_LIKE_ID_KEY = "SHARE_LIKE_ID_KEY_%s";
    private static final Integer LOCK_EXPIRE_TIME_SECONDS = 10;
    private static final Integer LOCK_WAIT_TIME_SECONDS = 6;

    private DistributeLock distributeLock;


    public Boolean sharelike(ShareLikeDo shareLikeDo){
        //给用户id加锁
        String lockey = String.format(SHARE_LIKE_ID_KEY,shareLikeDo.getUserId());
        String  uuid = UUIDUtil.getID();
        try {
            int waitTime = LOCK_WAIT_TIME_SECONDS * 1000;

            //循环调用，为了使改进程在阻塞后能执行,如果是让获取锁的执行逻辑，可以不加循环
            do{
                //锁住该用户id
                if (distributeLock.getRedisLock().lock(lockey,uuid,LOCK_EXPIRE_TIME_SECONDS)){



                    //获取锁并处理完加锁逻辑后跳出循环
                    break;
                }else { //若没有拿到锁，将该进程睡眠
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }while ((waitTime -= 100) >0 );  //自动解锁

        }catch (Exception e){
            return false;
        }finally {
            distributeLock.getRedisLock().unlock(lockey,uuid); //释放锁
        }
        return true;
    }

    public Boolean sharelikeRedisson(ShareLikeDo shareLikeDo){
        //给用户id加锁
        String lockey = String.format(SHARE_LIKE_ID_KEY,shareLikeDo.getUserId());
        String  uuid = UUIDUtil.getID();
        try {
            int waitTime = LOCK_WAIT_TIME_SECONDS * 1000;

            //循环调用，为了使改进程在阻塞后能执行,如果是让获取锁的执行逻辑，可以不加循环
            do{
                //锁住该用户id
                if (distributeLock.getRedisLock().lock(lockey,uuid,LOCK_EXPIRE_TIME_SECONDS)){



                    //获取锁并处理完加锁逻辑后跳出循环
                    break;
                }else { //若没有拿到锁，将该进程睡眠
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }while ((waitTime -= 100) >0 );  //自动解锁

        }catch (Exception e){
            return false;
        }finally {
            distributeLock.getRedisLock().unlock(lockey,uuid); //释放锁
        }
        return true;
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
