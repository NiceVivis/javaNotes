package com.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author yangwei
 * @date 2021/2/1 2:54 下午
 */
public class SpringServer {

    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("provider.xml");
        System.out.println("服务已暴漏");
        System.in.read();
    }
}
