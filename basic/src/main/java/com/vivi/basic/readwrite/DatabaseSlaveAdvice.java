package com.vivi.basic.readwrite;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DatabaseSlaveAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            DbContextHolder.slave();
            return methodInvocation.proceed();
        }finally {
            DbContextHolder.clear();
        }
    }
}
