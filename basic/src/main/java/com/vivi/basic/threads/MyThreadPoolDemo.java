package threads;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 第四种 获得java多线程的方式，线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        //ExecutorService executorService = Executors.newSingleThreadExecutor(); //一个池1个线程

        //ExecutorService executorService = Executors.newFixedThreadPool(5); //一个池5个线程

        ExecutorService executorService = Executors.newCachedThreadPool(); //一个池N个线程

        //模拟10个用户办理业务，每个用户就是一个外部的请求
        try{
            for (int i =1 ;i<10;i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
    }
}
