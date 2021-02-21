package com.vivi.basic.Concurrent;

public class SynchronizedTest {
    private static int i = 1;
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0;i < 100;i++){
            new Thread(()-> sync2()
            ).start();
        }
        System.out.printf("sum="+i);
    }
    public static void sync(){
        synchronized (SynchronizedTest.class){
            i++;
        }
    }

    public static synchronized void sync2(){
        i++;
    }

    public static void sum(){
        i++;
    }
}
