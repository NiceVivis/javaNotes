package com.redis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

/**
 * 记录所有运行中的线程信息，并导出有效信息
 * @author hongwm
 */
public class ThreadInfo {
    private String threadName;
    private int priority;
    private long tid;
    private String state;

    /**
     * 线程栈
     */
    private StackTraceElement[] stacks;

    /**
     * 等待中的锁
     */
    private Object waitingLock;

    /**
     * synchronized object
     */
    private Object syncObject;

    public ThreadInfo(Thread t, StackTraceElement[] stacks) {
        this.threadName = t.getName();
        this.priority = t.getPriority();
        this.tid = t.getId();
        this.state = t.getState().toString();
        this.waitingLock = LockSupport.getBlocker(t);
        this.syncObject = getSynchonizedObject(t);
        this.stacks = stacks;
    }

    protected Object getSynchonizedObject(Thread t) {
        Field[] fs = t.getClass().getDeclaredFields();
        for(Field f : fs) {
            if(f.getName().startsWith("val")) {
                f.setAccessible(true);
                try {
                    return f.get(t);
                } catch (Throwable e) {
                    return null;
                }
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(threadName).append("\" ")
                .append("prio=").append(priority).append(" ")
                .append("tid=").append(tid).append(" ")
                .append(state).append(" ")
        ;

        if(waitingLock != null) {
            sb.append("on object ").append(Integer.toHexString(waitingLock.hashCode()));
        } else if(syncObject != null) {
            sb.append("on object ").append(Integer.toHexString(syncObject.hashCode()));
        }
        sb.append('\n');
        if(stacks != null) {
            for(StackTraceElement stack : stacks) {
                sb.append("    at ").append(stack.toString()).append('\n');
            }
        }

        return sb.toString();
    }

    public static List<ThreadInfo> getAllThreadInfos() {
        List<ThreadInfo> threads = new ArrayList<ThreadInfo>();

        Map<Thread, StackTraceElement[]> m = Thread.getAllStackTraces();
        for(Map.Entry<Thread, StackTraceElement[]> e : m.entrySet()) {
            Thread t = e.getKey();
            StackTraceElement[] stacks = e.getValue();
            threads.add(new ThreadInfo(t, stacks));
        }

        Collections.sort(threads, new Comparator<ThreadInfo>() {
            @Override
            public int compare(ThreadInfo o1, ThreadInfo o2) {
                return o1.threadName.compareToIgnoreCase(o2.threadName);
            }
        });

        return threads;
    }

}
