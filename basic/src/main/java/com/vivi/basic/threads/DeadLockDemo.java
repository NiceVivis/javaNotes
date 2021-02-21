package com.vivi.basic.threads;

import java.util.concurrent.TimeUnit;

/**
 * 1、死锁就是2个或2个以上的线程在执行的过程中，因为争夺资源而造成的一种相互等待的现象
 *
 * 2、死锁的排查 linux ps -ef|grep xxx
 *             windows
 *                 jps = java ps    jps -l
 */
public class DeadLockDemo implements Runnable{

    private String LockA;
    private String LockB;

    public DeadLockDemo(String lockA, String lockB) {
        LockA = lockA;
        LockB = lockB;
    }

    @Override
    public void run() {
        synchronized (LockA){
            System.out.println(Thread.currentThread().getName()+"\t 自己持有"+LockA+"尝试获取："+LockB);
            try {
                TimeUnit.SECONDS.sleep(2);
                synchronized (LockB){
                    System.out.println(Thread.currentThread().getName()+"\t 自己持有"+LockA+"尝试获取："+LockB);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String lockA = "lockA";
        String locaB = "locakB";
        new Thread(new DeadLockDemo(lockA,locaB),"AAA").start();
        new Thread(new DeadLockDemo(locaB,lockA),"BBB").start();
    }
}
