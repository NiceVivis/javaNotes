package com.vivi.basic.threads;

import java.util.concurrent.CountDownLatch;

/**
 * 做减法
 * 让一些线程阻塞直到另一些线程完成一系列操作才被唤醒
 *
 * await方法，调用线程被阻塞。
 * 其他线程调用countDown方法会将计数器减1，当计数器为0时，await方法被阻塞的线程会被唤醒。
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {

    }

    public static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);


        for (int i=0;i<=6;i++){
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName()+"\t上完自习，离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t班长最后走人");
    }
}
