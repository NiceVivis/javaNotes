package com.redis;

public interface MetricMap {

    /**
     * Integer类型
     * @param value
     * @param keys
     */
    void set(int value, String... keys);

    /**
     * Long类型
     * @param value
     * @param keys
     */
    void set(long value, String... keys);

    /**
     * Double类型
     * @param value
     * @param keys
     */
    void set(double value, String... keys);

    /**
     * float类型
     * @param value
     * @param keys
     */
    void set(float value, String... keys);

    /**
     * boolean类型
     * @param value
     * @param keys
     */
    void set(boolean value, String... keys);
}

