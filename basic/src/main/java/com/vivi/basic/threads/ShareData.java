package threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者
 */
public class ShareData {
    private int number =0;
    private Lock lock = new ReentrantLock();
    private Condition condition =lock.newCondition();

    public void increment() throws Exception {
        lock.lock();
        try {
            //判断
            while (number != 0){
                //等待
                condition.await();
            }
            //工作
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void deincrement() throws Exception {
        lock.lock();
        try {
            //判断
            while (number == 0){ //如果用 if(number == 0) ，超过2个线程的时候，会出现异常。
                //等待
                condition.await();
            }
            //工作
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
class ProConsumer{
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(()->{
            for (int i =1 ;i<=5 ;i++){
                try{
                    shareData.increment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(()->{
            for (int i =1 ;i<=5 ;i++){
                try{
                    shareData.deincrement();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"BB").start();
    }
}
