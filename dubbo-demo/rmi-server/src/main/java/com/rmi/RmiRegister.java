package com.rmi;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;

/**
 * rmi注册中心
 * @author yangwei
 * @date 2021/1/25 6:22 下午
 */
public class RmiRegister {

    public static void main(String[] args) throws IOException {
        LocateRegistry.createRegistry(8080);
        System.out.println("注册中心已启动");
        System.in.read();//不让进程关闭
    }
}
