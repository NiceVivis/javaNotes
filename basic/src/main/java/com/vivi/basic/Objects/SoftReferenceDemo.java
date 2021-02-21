package com.vivi.basic.Objects;

import java.lang.ref.SoftReference;

/**
 * 软引用，内存不足gc 的时候会被回收
 * 内存够用的时候不会被回收
 */
public class SoftReferenceDemo {
    public static void softRe(){
        Object object1 = new Object();
        SoftReference<Object> objectSoftReference = new SoftReference<>(object1);
        System.out.println(object1);
        System.out.println(objectSoftReference.get());
        object1 = null;
        System.gc();

        System.out.println(object1);
        System.out.println(objectSoftReference.get());
    }

    public static void softRe_Not(){
        Object object1 = new Object();
        SoftReference<Object> objectSoftReference = new SoftReference<>(object1);
        System.out.println(object1);
        System.out.println(objectSoftReference.get());
        object1 = null;


        try {
            byte[] bytes = new byte[30*1024*1024];
        }catch (Exception e){
            System.out.println(object1);
            System.out.println(objectSoftReference.get());
            e.printStackTrace();
        }
    }

}
