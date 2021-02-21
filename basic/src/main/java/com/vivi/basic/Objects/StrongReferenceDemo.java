package com.vivi.basic.Objects;

/**
 * 强引用
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object object1 = new Object();
        Object object2 = object1;
        object1 = null;
        System.gc();
        System.out.println(object2);

    }
}
