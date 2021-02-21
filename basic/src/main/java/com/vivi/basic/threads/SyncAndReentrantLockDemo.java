package threads;

import java.util.concurrent.locks.ReentrantLock;

/**1、原始构成
 *    synchronized是关键字 属于jvm层面的
 *        monitorenter底层是通过monitor对象来完成的，
 *        monitorexit
 *    Lock是具体类
 * 2、使用方法
 *     synchronized不需要用户去手动释放锁，当 synchronized代码执行完后系统会自动让线程释放对锁的占用
 *     ReentrantLock需要用户去手动释放锁，若没有主动释放锁，就可能造成死锁的现象 （unlock）
 * 3、等待是否可中断
 *     synchronized不可中断,除非报异常
 *     ReentrantLock可中断 ，1 设置超时方法，tryLock(Long timeout，TImeunit unit)
 *                          2、调用interrupt方法可中断
 *  4、加锁是否公平
 *     synchronized 非公平锁
 *     ReentrantLock 默认非公平锁，构造方法可以传入boolean，true为公平锁，false为非公平锁
 *  5、
 */
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        synchronized (new Object()){ //monitorenter 和monitorexit 不会造成死锁

        }
        new ReentrantLock();
    }
}
