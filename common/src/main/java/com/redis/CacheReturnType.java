package com.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@Builder
public class CacheReturnType<T> {
    private boolean withReturn;
    private T result;
    private Map<String, Object> params;
}
