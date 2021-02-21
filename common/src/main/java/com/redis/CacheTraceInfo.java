package com.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class CacheTraceInfo extends RequestTraceInfo {
    // 请求类型,cache或redis
    private String type;
    //    private Integer expire;
//    private String method;
//    private String key;
    // 请求参数
    private Map<String, Object> extparams;
    // 返回的参数
    private Map<String, Object> params;
}
