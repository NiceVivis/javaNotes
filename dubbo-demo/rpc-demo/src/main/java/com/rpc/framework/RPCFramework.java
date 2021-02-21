package com.rpc.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yangwei
 * @date 2021/1/29 11:39 上午
 */
public class RPCFramework {

    public static void export(Object service,int port) throws Exception {

        ServerSocket server = new ServerSocket(port);
        while (true){
            Socket socket = server.accept();

            new Thread(() -> {
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    String methodName = String.valueOf(inputStream.read()); // 读取方法名
                    Class<?>[] parameterTypes = (Class<?>[]) inputStream.readObject(); //参数类型

                    Object[] arguments = (Object[])inputStream.readObject(); //参数
                    Method method = service.getClass().getMethod(methodName,parameterTypes); // 找到方法
                    Object result = method.invoke(service,arguments); //调用方法

                    //返回结果
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }

    public static <T> T refer(Class<T> interfaceClass, String host, int port){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{
                        interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = new Socket(host,port); //指定 provider 的 ip 和端口
                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//                        outputStream.write(Integer.parseInt(method.getName())); //传方法名
                        outputStream.writeObject(method.getParameterTypes()); //传参数类型
                        outputStream.writeObject(args); //传参数值

                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        Object result = inputStream.readObject(); // 读取结果

                        return result;
                    }
                });
    }
}
