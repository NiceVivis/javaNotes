package com.redis;

public enum LogErrorType {
    FAULT(1), FAIL(2);

    private int value;

    LogErrorType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
