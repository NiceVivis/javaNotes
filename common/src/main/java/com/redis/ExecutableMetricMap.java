package com.redis;

public abstract class ExecutableMetricMap extends AbstractMetricMap{

    private String name;

    public abstract void collect();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

