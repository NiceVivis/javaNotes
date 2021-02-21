package com.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author yangwei
 * @date 2021/1/25 6:22 下午
 */
public interface UserService extends Remote {

    String getName(int id) throws RemoteException;
}
