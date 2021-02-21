package com.dubbo;

import com.client.User;
import com.client.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author yangwei
 * @date 2021/2/1 2:13 下午
 */
public class DubboClient {

    /**
     * 客户端核心对象
     * ApplicationConfig
     * ReferenceConfig
     *
     * 1、暴漏 2、引用
     * @param args
     */
//    public static void main(String[] args) throws IOException {
//        ApplicationConfig applicationConfig = new ApplicationConfig("young-app");
//
//        RegistryConfig registryConfig = new RegistryConfig("multicast://224.1.1.1:3333"); //虚拟的注册中心，局域网里才能用
//
//        ReferenceConfig referenceConfig = new ReferenceConfig();
//        referenceConfig.setRegistry(registryConfig);
//        referenceConfig.setApplication(applicationConfig);
//        referenceConfig.setInterface(UserService.class);
//        referenceConfig.setGroup("xing"); //调用唯一的组
//        //referenceConfig.setUrl("");
//        referenceConfig.setLoadbalance("roundrobin"); //负载均衡，轮询
//        UserService userService = (UserService) referenceConfig.get();
//
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        while (true) {
//            if (bufferedReader.readLine().equals("quit")) {
//                break;
//            } else {
//                System.out.println(userService.getUser(1));
//            }
//        }
//    }

    public static void main(String[] args) throws IOException {
        ApplicationConfig applicationConfig = new ApplicationConfig("young-app");

        RegistryConfig registryConfig = new RegistryConfig("multicast://224.1.1.1:3333"); //虚拟的注册中心，局域网里才能用

        ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setInterface(UserService.class);
        referenceConfig.setGroup("xing"); //调用唯一的组
        //referenceConfig.setUrl("");
        referenceConfig.setLoadbalance("roundrobin"); //负载均衡，轮询
        UserService userService = (UserService) referenceConfig.get();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = bufferedReader.readLine();
            if (line.equals("quit")) {
                break;
            } else {
                System.out.println(userService.getUser(1));
            }

            if (line.startsWith("findUser")){
                List<User> byCity = userService.findUser(line.split("")[1],line.split(" ")[2]);
                String s = Arrays.toString(byCity.toArray());
                System.out.println(s);
            }else if (line.startsWith("getUser")){
                System.out.println(userService.getUser(1));
            }else {
                long begin = System.currentTimeMillis();
                userService.getUser(1);
                Future<User> future = RpcContext.getContext().getFuture(); //异步调用

                userService.getUser(1);
                Future<User> future2 = RpcContext.getContext().getFuture();

                try {
                    User user = future.get(); //等待，get是阻塞
                    User user1 = future2.get();//等待

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                //System.out.println(userService.getUser(1));
                System.out.println(System.currentTimeMillis()-begin);
            }
        }
    }
}
