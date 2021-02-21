package com.rmi;

import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * 不算远程调用，只是将远程调用的对象传了过来，其实还是本地调用。可以用作远程传输
 * rmi 不能实现负载均衡，不能跨语言。
 * @author yangwei
 * @date 2021/1/26 10:35 上午
 */
public class RmiClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        UserService userService = (UserService) Naming.lookup("rmi://localhost:8080/UserService");
        //UserService userService = (UserService) Naming.lookup("rmi://localhost:8080/UserService_1"); // 可以发布多个服务
        System.out.println(String.format("引用成功: %s", ManagementFactory.getRuntimeMXBean().getName()));
        System.out.println(userService.getName(11));

    }
}
