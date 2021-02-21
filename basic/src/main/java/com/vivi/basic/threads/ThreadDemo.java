package threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThrea implements Runnable{
    @Override
    public void run() {

    }
}

class MyCallable implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("callable");
        return 100;
    }
}
//ThreadPoolExecutor
public class ThreadDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
        Thread t1 =new Thread(futureTask,"AA");
        t1.start();
        System.out.println("返回值"+futureTask.get());
    }
}
