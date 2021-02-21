package com.vivi.basic.producerConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用阻塞队列实现生产者消费者模型
 */
class BlockingQueueTest{

    private static final int MAX_CAPACITY = 10;//阻塞队列容量
    private static BlockingQueue<Integer> blockingDeque = new ArrayBlockingQueue<>(MAX_CAPACITY);//阻塞队列容量
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    public void produce() throws InterruptedException {
        while (FLAG){
            boolean retvalue = blockingDeque.offer(atomicInteger.incrementAndGet(),2, TimeUnit.SECONDS);
            if (retvalue == true){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+atomicInteger.get()+"成功"+"资源队列大小="+blockingDeque.size());
            }else {
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+atomicInteger.get()+"失败"+"资源队列大小="+blockingDeque.size());
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"FLAG变成flase,生产停止");
    }

    public void consume() throws InterruptedException {
        Integer result = null;
        while (true){
            result = blockingDeque.poll(2,TimeUnit.SECONDS);
            if (result == null){
                System.out.println("超过两秒没有取到数据，消费者即将退出");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t 消费"+result+"成功"+"\t\t"+"资源队列大小"+blockingDeque.size());
            Thread.sleep(1500);
        }
    }
    public void stop(){
        this.FLAG = false;
    }
}
public class producerConsumer2 {
    public static void main(String[] args) {
        BlockingQueueTest test2 = new BlockingQueueTest();
        new Thread(() ->{
            try {
                test2.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA").start();

        new Thread(() ->{
            try {
                test2.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB").start();

        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        test2.stop();
    }
}
