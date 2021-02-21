package com.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target({ElementType.FIELD})  标明注解的修饰目标
 * 用来指定注解修饰类的哪个成员 {}里表示一个数组，指被修饰的注解能有用于多个不同的类成员，FIELD表示只能用来修饰类中的Field
 *
 * @Retention
 * 指Annotation(注释)被保留的时间长短，标明注解的生命周期，有三种RetentionPolicy取
 * 1、RetentionPolicy.SOURCE
 *    注解只保留在源文件，当java文件编译成class文件的时间，注解被遗弃
 * 2、RetentionPolicy.CLASS
 *    注解被保留到class文件，但jvm加载class文件时被遗弃，这是默认的生命周期
 * 3、RetentionPolicy.RUNTIME
 *    注解不仅被保留到class文件中，jvm加载class文件之后，仍然存在
 *
 * @Documented
 * 标记注解，用于描述其他类型的注解应该被作为被标注的程序成员的公共API，因此可以被 javadoc此类的工具文档化
 *
 * @Inherited
 * 标记注解，允许子类继承父类的注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VAutowired {
    String value() default "";
}
