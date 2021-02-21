package com.vivi.basic.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 初始值为0，做加法
 * CyclicBarrier可循环使用的屏障，让一组线程到达一个屏障时被阻塞，直到最后一个线程到达屏障，屏障才会开门。
 * 所有被屏障拦截的线程才会继续干活。线程进入屏障通过await方法。
 *
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> {System.out.println("***召唤神龙");});

        for (int i=0;i<=6;i++){
            final int tempInt = i;
            System.out.println(Thread.currentThread().getName()+"\t收集到"+tempInt+"龙珠");
            new Thread(() ->{
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }

}
