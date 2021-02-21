package com.redis;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseTraceInfo {
    private String exceptionStr;
    private Long warningCost;
}
