package com.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CacheMethodReturnType<T> {
    private boolean success;
    private T result;
}

