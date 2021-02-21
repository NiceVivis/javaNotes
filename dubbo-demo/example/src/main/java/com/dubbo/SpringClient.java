package com.dubbo;

import com.client.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author yangwei
 * @date 2021/2/1 2:54 下午
 */
public class SpringClient {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("consumer.xml");

        UserService userService = context.getBean(UserService.class);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            if (bufferedReader.readLine().equals("quit")) {
                break;
            }
            System.out.println(userService.getUser(1));
        }
    }
}
