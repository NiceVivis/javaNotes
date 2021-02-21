package com.redis;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;

public class JVMMetrics extends ExecutableMetricMap {

    @Override
    public void collect() {
        super.setName("jvm");
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();

        super.set((memory.getHeapMemoryUsage().getInit() / 1024 / 1024L), "heapInit");
        super.set((memory.getHeapMemoryUsage().getMax() / 1024 / 1024L), "heapMax");
        super.set((memory.getHeapMemoryUsage().getUsed() / 1024 / 1024L), "heapUsed");
        super.set((memory.getHeapMemoryUsage().getCommitted() / 1024 / 1024L), "heapCommitted");

        super.set((memory.getNonHeapMemoryUsage().getInit() / 1024 / 1024L), "nonHeapInit");
        super.set((memory.getNonHeapMemoryUsage().getMax() / 1024 / 1024L), "nonHeapMax");
        super.set((memory.getNonHeapMemoryUsage().getUsed() / 1024 / 1024L), "nonHeapUsed");
        super.set((memory.getNonHeapMemoryUsage().getCommitted() / 1024 / 1024L), "nonHeapCommitted");

        OperatingSystemMXBean op = ManagementFactory.getOperatingSystemMXBean();
        super.set(op.getSystemLoadAverage(), "systemLoadAverage");

        ThreadMXBean thread = ManagementFactory.getThreadMXBean();

        super.set(thread.getThreadCount(), "threadCount");
        super.set(thread.getPeakThreadCount(), "peakThreadCount");
        super.set(thread.getDaemonThreadCount(), "daemonThreadCount");

        List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean gc : gcs){
            String name = gc.getName().replace(" ", ".").toLowerCase();
            super.set(gc.getCollectionCount(), name,"collectionCount");
            super.set(gc.getCollectionTime(), name, "collectionTime");
        }

        List<MemoryPoolMXBean> mps = ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean mp : mps){
            String name = mp.getName().replace(" ", ".").toLowerCase();
            if(mp.getCollectionUsage()!=null){
                super.set((mp.getCollectionUsage().getInit() / 1024 / 1024L), name, "collectionUsageInited");
                super.set((mp.getCollectionUsage().getMax() / 1024 / 1024L), name, "collectionUsageMax");
                super.set((mp.getCollectionUsage().getUsed() / 1024 / 1024L), name, "collectionUsageUsed");
                super.set((mp.getCollectionUsage().getCommitted() / 1024 / 1024L), name, "collectionUsageCommitted");
            }
        }
    }

//    /**
//     * Java 虚拟机的运行时系统
//     */
//    private Map<String,String> getJvmInfo() {
//        Map<String,String> map = new TreeMap<String,String>();
//        RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
//        map.put("jvm vendor" , mxbean.getVmVendor());
//        map.put("jvm name:" , mxbean.getVmName());
//        map.put("jvm version:" , mxbean.getVmVersion());
//        map.put("jvm bootClassPath:" , mxbean.getBootClassPath());
//        map.put("jvm start time:" , mxbean.getStartTime()+"");
//        return map;
//    }
//
//    /**
//     * Java 虚拟机在其上运行的操作系统
//     */
//    private Map<String,String> getSystem() {
//        OperatingSystemMXBean op = ManagementFactory.getOperatingSystemMXBean();
//
//        Map<String, String> map = new TreeMap<String,String>();
//        map.put("Architecture", op.getArch());
//        map.put("Processors", op.getAvailableProcessors()+"");
//        map.put("System name", op.getName());
//        map.put("System version", op.getVersion());
//        map.put("Last minute load", op.getSystemLoadAverage()+"");
//        map.put("Host", SystemInfoUtil.getHostName());
//        map.put("IP",SystemInfoUtil.getHostIP());
//        return map;
//    }
//
//    /**
//     * Java 虚拟机的类加载系统
//     */
//    private Map<String,String> getClassLoading(){
//        ClassLoadingMXBean cl = ManagementFactory.getClassLoadingMXBean();
//
//        Map<String, String> map = new TreeMap<String,String>();
//        map.put("TotalLoadedClassCount", cl.getTotalLoadedClassCount()+"");
//        map.put("LoadedClassCount", cl.getLoadedClassCount()+"");
//        map.put("UnloadedClassCount", cl.getUnloadedClassCount()+"");
//        return map;
//    }
//
//    /**
//     * Java 虚拟机的编译系统
//     */
//    private Map<String,String> getCompilation(){
//        CompilationMXBean com = ManagementFactory.getCompilationMXBean();
//
//        Map<String, String> map = new TreeMap<String,String>();
//        map.put("TotalCompilationTime" , com.getTotalCompilationTime()+"");
//        map.put("name" , com.getName());
//        return map;
//    }
//
//    /**
//     * Java 虚拟机的线程系统
//     */
//    private Map<String,String> getThread(){
//        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
//
//        Map<String, String> map = new TreeMap<String,String>();
//        map.put("threadCount", thread.getThreadCount()+"");
//        map.put("peakThreadCount", thread.getPeakThreadCount()+"");
//        map.put("daemonThreadCount", thread.getDaemonThreadCount()+"");
//        map.put("CurrentThreadUserTime", thread.getCurrentThreadUserTime()+"");
//        return map;
//    }
//
//    /**
//     * Java 虚拟机中的垃圾回收器。
//     */
//    private Map<String,String> getGarbageCollector(){
//        List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
//        Map<String, String> map = new TreeMap<String,String>();
//        for(GarbageCollectorMXBean gc : gcs){
//            String name = gc.getName().replace(" ", "-").toLowerCase();
//            map.put(name+"-collectionCount", gc.getCollectionCount()+"");
//            map.put(name+"-collectionTime", gc.getCollectionTime()+"");
//        }
//        return map;
//    }
//
//    /**
//     * Java 虚拟机中的内存管理器
//     */
//    private Map<String,String> getMemoryManager(){
//        List<MemoryManagerMXBean> mm = ManagementFactory.getMemoryManagerMXBeans();
//        Map<String, String> map = new TreeMap<String,String>();
//        for(MemoryManagerMXBean eachmm: mm){
//            String name = eachmm.getName().replace(" ", "-").toLowerCase();
//            String[] poolNames = eachmm.getMemoryPoolNames();
//            String pooledName = "";
//            for(String poolName : poolNames){
//                pooledName = poolName + " ";
//            }
//            map.put(name+"-memoryPoolNames", pooledName);
//        }
//        return map;
//    }
//
//    /**
//     * Java 虚拟机中的内存池
//     */
//    private Map<String,String> getMemoryPool(){
//        List<MemoryPoolMXBean> mps = ManagementFactory.getMemoryPoolMXBeans();
//        Map<String, String> map = new TreeMap<String,String>();
//        for(MemoryPoolMXBean mp : mps){
//            String name = mp.getName().replace(" ", "-").toLowerCase();
//            if(mp.getCollectionUsage()!=null){
//                map.put(name+"-collectionUsageInited", (mp.getCollectionUsage().getInit() / 1024 / 1024L) + "MB");
//                map.put(name+"-collectionUsageMax", (mp.getCollectionUsage().getMax() / 1024 / 1024L) + "MB");
//                map.put(name+"-collectionUsageUsed", (mp.getCollectionUsage().getUsed() / 1024 / 1024L) + "MB");
//                map.put(name+"-collectionUsageCommitted", (mp.getCollectionUsage().getCommitted() / 1024 / 1024L) + "MB");
//            }
//            if(mp.getType()!=null){
//                map.put(name+"type", mp.getType().name());
//            }
//
//        }
//        return map;
//    }
}

