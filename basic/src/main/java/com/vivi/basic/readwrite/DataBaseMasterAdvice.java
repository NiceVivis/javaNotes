package com.vivi.basic.readwrite;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DataBaseMasterAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            DbContextHolder.master();
            return methodInvocation.proceed();
        }finally {
            DbContextHolder.clear();
        }
    }
}
