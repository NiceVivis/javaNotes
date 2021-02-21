package com.rmi;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;

/**
 * @author yangwei
 * @date 2021/1/26 10:27 上午
 */
public class RmiServer {
    public static void main(String[] args) throws IOException, AlreadyBoundException {
        UserService userService = new UserServiceImpl();
        Naming.bind("rmi://localhost:8080/UserService",userService);
        System.out.println("服务已发布");
        System.in.read();//不能关闭进程
    }
}
