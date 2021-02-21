package threads;

import java.sql.Time;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者
 */
public class ProdConsumer_BlockQueue {

    public static void main(String[] args) {
        Resource resource = new Resource(new ArrayBlockingQueue<>(10));
        new Thread(() ->{
            System.out.println(Thread.currentThread().getName()+"\t生产线程启动");
            try {
                resource.myprod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Prod").start();

        new Thread(() ->{
            System.out.println(Thread.currentThread().getName()+"\t消费线程启动");
            try {
                resource.myConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("线程停止");
        resource.stop();
    }
}
class Resource{
    private volatile boolean flag =true; //默认开启,进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public Resource(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myprod() throws InterruptedException {
        String data = null;
        boolean retValue;
        while (flag){
            data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2, TimeUnit.SECONDS);
            if (retValue){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"成功") ;
            }else {
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"失败") ;
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t生产动作结束");
    }

    public void myConsumer() throws InterruptedException {
        String result = null;
        while (true){
            result = blockingQueue.poll(2,TimeUnit.SECONDS);
            if (result == null){
                //flag = false;
                System.out.println(Thread.currentThread().getName()+"\t超过2秒钟没有消息，消费退出");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t消息队列"+result+"成功");
        }
    }


    public void stop() {
        this.flag = false;
    }
}
