package com.dubbo;

import com.client.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author yangwei
 * @date 2021/2/1 4:47 下午
 */
@SpringBootApplication
@EnableDubbo
public class DemoApplication {

    @Reference
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }

    @Bean
    public ApplicationRunner getBean(){
        return args->{
            System.out.println(userService.getUser(1));
        };
    }
}
