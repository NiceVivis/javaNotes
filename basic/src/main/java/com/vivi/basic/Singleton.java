package com.vivi.basic;

/**
 * 单例模式
 */
public class Singleton {
    private static volatile  Singleton singleton = null;

    public Singleton(){
        System.out.println(Thread.currentThread().getName() + "我的构造方法");
    }

    public static Singleton getSingleton(){
        if (singleton !=null){
            synchronized (Singleton.class) {
                if (singleton !=null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        //System.out.println(Singleton.getSingleton() == Singleton.getSingleton());
        //System.out.println(Singleton.getSingleton() == Singleton.getSingleton());

        for (int i = 1 ;i<= 10;i++){
            // new Thread(String.valueOf(Singleton.getSingleton())).start();
            new Thread(() -> {
                Singleton.getSingleton();
            },String.valueOf(i)).start();
        }
    }
}
