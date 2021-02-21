package com.vivi.basic.producerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者和消费者在同一时间段内共用同一个存储空间，生产者往存储空间中添加产品，消费者从存储空间中取走产品
 * 当存储空间为空时，消费者阻塞，当存储空间满时，生产者阻塞
 *
 * 实现生产者消费者的方式：
 * 1、使用synchronized的以及wait(),notify()/notifyAll()
 */
class SynchronizedTest {

    public static AtomicInteger atomicInteger = new AtomicInteger();
    public volatile boolean flag = true;
    public static final int MAX_COUNT = 10;
    public static final List<Integer> pool = new ArrayList<>();

    //生产者
    public void produce(){
        while (flag){
            try {
                //每隔 1000 毫秒生产一个商品
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (pool){
                //如果pool满了，生产者停止生产
                //这里如果用if会怎么样？
                while (pool.size() == MAX_COUNT) {
                    try {
                        System.out.println("pool is full,waiting");
                        //停止线程，释放当前线程的cpu
                        pool.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //生产
                pool.add(atomicInteger.incrementAndGet());
                System.out.println("produce number:"+atomicInteger.get()+"\t"+"current size"+pool.size());
                //唤醒所以的线程
                pool.notifyAll();
            }
        }
    }
    //消费者
    public void consumue(){
        //判断当前状态
        while (flag){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (pool){
                //如果这里是if的话,我们有多个消费者，多线程的环境下可能会造成虚假唤醒。所以我们判断条件要用while不能用if
                 if(pool.size() == 0){
                    try {
                        System.out.println("pool is empty,wating....");
                        pool.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int temp = pool.get(0);
                pool.remove(0);
                System.out.println("cousume number:"+temp+"\t"+"current size:"+pool.size());
                //通知
                pool.notifyAll();
            }
        }
    }
    public void stop(){
        flag = false;
    }
}
public class producerConsumer1{
    public static void main(String[] args) {
        SynchronizedTest producerTest1 = new SynchronizedTest();
        new Thread(() ->{
            producerTest1.produce();
        },"AAA").start();

        new Thread(() ->{
            producerTest1.consumue();
        },"BBB").start();

//        new Thread(() ->{
//            producerTest1.consumue();
//        },"CCC").start();
//
//        new Thread(() ->{
//            producerTest1.consumue();
//        },"DDD").start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producerTest1.stop();
    }
}
