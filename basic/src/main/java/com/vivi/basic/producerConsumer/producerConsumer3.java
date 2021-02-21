package com.vivi.basic.producerConsumer;

import java.util.concurrent.Semaphore;

/**
 * 信号量Semaphore实现生产者和消费者
 * 信号量是用来控制同时访问特定资源的线程数量，它通过协调各个线程，以保证合理的使用公共资源。
 * java中Semaphore维护了一个许可集，一开始先设定这个许可集的数量，可以使用acquire() 方法获得一个许可，
 * 当许可不足时会被阻塞，release()添加一个许可。
 */
class SemaphoreTest{
    private static Integer count = 0;
    //创建三个信号量
    final Semaphore notFull = new Semaphore(10);
    final Semaphore notEmpty = new Semaphore(0);
    //mutex信号量，维护生产者消费者之间的同步关系，保证生产者和消息者之间的交替进行
    final Semaphore mutex = new Semaphore(1);

    class Producer implements Runnable{

        @Override
        public void run() {
            for (int i=0;i<10;i++){
                try {
                    Thread.sleep(3000);
                    notFull.acquire();
                    mutex.acquire();
                    count++;
                    System.out.println(Thread.currentThread().getName()+"生产者生产，目前总共有"+count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    mutex.release();
                    notEmpty.release();
                }

            }
        }
    }

    class Consumer implements Runnable{

        @Override
        public void run() {
            for (int i=0;i<10;i++){
                try {
                    Thread.sleep(3000);
                    notEmpty.acquire();
                    mutex.acquire();
                    count--;
                    System.out.println(Thread.currentThread().getName()+"消费者消费，目前总共有"+count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    mutex.release();
                    notFull.release();
                }
            }
        }
    }
}
public class producerConsumer3 {
    public static void main(String[] args) {
        SemaphoreTest semaphoreTest = new SemaphoreTest();
        new Thread(semaphoreTest.new Producer()).start();
        new Thread(semaphoreTest.new Consumer()).start();
        new Thread(semaphoreTest.new Producer()).start();
        new Thread(semaphoreTest.new Consumer()).start();
        new Thread(semaphoreTest.new Producer()).start();
        new Thread(semaphoreTest.new Consumer()).start();
    }
}
