package com.rmi;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author yangwei
 * @date 2021/1/26 10:20 上午
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService, Serializable { //实现Serializable ，实例化

    protected UserServiceImpl() throws RemoteException {
    }

    @Override
    public String getName(int id) {
        return String.format("hello world: %s", ManagementFactory.getRuntimeMXBean().getName());//获取当前进程
    }
}
