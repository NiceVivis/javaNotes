package com.vivi.basic.threads;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * cas
 * 比较和交换
 */
public class CasDemo {

    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5,2019)+""+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5,1008)+""+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(2019,1008)+""+atomicInteger.get());
    }
}
