package com.vivi.basic.readwrite;

import java.util.Objects;

public class DbContextHolder {

    public static final String MASTER = "master";
    public static final String SLAVE = "slave";
    /**
     * 读写配置
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
    /**
     * 读写层级
     */
    private static final ThreadLocal<Integer> CONTEXT_LAYER = new ThreadLocal<>();

    public static String get() {
        return CONTEXT_HOLDER.get();
    }

    public static void set(String dbType) {
        Integer layer = CONTEXT_LAYER.get();
        if (Objects.isNull(layer)) {
            CONTEXT_LAYER.set(0);
            CONTEXT_HOLDER.set(dbType);
        } else {
            layer = layer + 1;
            CONTEXT_LAYER.set(layer);
        }
    }

    static void clear() {
        Integer layer = CONTEXT_LAYER.get();
        if (Objects.isNull(layer) || layer.equals(0)) {
            CONTEXT_LAYER.remove();
            CONTEXT_HOLDER.remove();
        } else {
            layer = layer - 1;
            CONTEXT_LAYER.set(layer);
        }
    }

    static void master() {
        set(MASTER);
    }

    static void slave() {
        set(SLAVE);
    }
}
