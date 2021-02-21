package com.vivi.basic.Objects;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 弱引用   不够内存够不够，gc的时候都会被回收
 *
 *
 * 虚引用 如果一个对象仅持有虚引用，那么它就和没有引用一样，在任何时候都可能被垃圾回收器回收
 * 必须和引用队列联合使用
 *
 * 对象在对回收前收到一个系统通知或者后续添加进一步的处理
 */
public class WeakRerenceDemo {

    public static void main(String[] args) {
        Object object1 = new Object();
        WeakReference<Object> objectweakReference = new WeakReference<>(object1);
        System.out.println(object1);
        System.out.println(objectweakReference.get());

        object1 = null;

        System.gc();

        System.out.println(object1);
        System.out.println(objectweakReference.get());
    }
}
