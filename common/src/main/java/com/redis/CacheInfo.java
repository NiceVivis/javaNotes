package com.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@Builder
@Setter
@Getter
public class CacheInfo {
    private CacheOpsType cacheOpsType;
    private String key;
    //    private String value;
    private Long expire;
    private TimeUnit unit;
    private Long len;
    private Integer start;
    private Integer end;
}

