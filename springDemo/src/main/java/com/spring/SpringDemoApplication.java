package com.spring;

/**
 * spring框架的思路：
 * 1、配置阶段
 *    配置web.xml文件
 *    <servlet>
 *        设定 init-param
 *        <servlet-mapping>
 *            设定url-pattern：/*
 *  配置Annotation 注解
 *
 *  2、初始化阶段
 *     IOC：
 *       调用init()方法：加载配置文件
 *       IOC容器初始化：Map<String,Object>
 *       扫描相关的类：scan-package = "com."
 *       创建实例化并保存到容器：通过反射机制将类实例化放入IOC容器中
 *     DI：
 *       进行DI操作：扫描IOC容器中的实例，给每天赋值的属性自动赋值
 *
 */
public class SpringDemoApplication {
}
