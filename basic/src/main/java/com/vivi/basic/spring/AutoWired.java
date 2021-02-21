package com.vivi.basic.spring;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)  //当前注解运行的范围
@Target(ElementType.FIELD)     //当前属性作用在什么地方上
public @interface AutoWired {
}
