package com.dubbo.boot;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yangwei
 * @date 2021/2/1 4:18 下午
 */
@SpringBootApplication
@EnableDubbo
public class BootServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootServerApplication.class,args);
        System.out.println("服务已开启");
    }
}
