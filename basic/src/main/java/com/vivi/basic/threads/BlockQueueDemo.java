package com.vivi.basic.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 队列
 */
public class BlockQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        //System.out.println(""+blockingQueue.add("0"));
        //System.out.println(""+blockingQueue.add("1"));
        //System.out.println(""+blockingQueue.add("2")); //长度为3，超过3报错

        //System.out.println(blockingQueue.element());

        //System.out.println(blockingQueue.remove());//删除长度超过3，报错

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d")); //长度为3，超过3不报错，会显示为false

        System.out.println(blockingQueue.peek()); //队列先进先出， 显示第一个值，a

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll()); //删除长度超过3，则显示为null
    }
}
