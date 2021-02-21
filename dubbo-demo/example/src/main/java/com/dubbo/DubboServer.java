package com.dubbo;

import org.apache.dubbo.config.*;
import org.apache.dubbo.registry.RegistryService;
import org.apache.dubbo.registry.integration.RegistryDirectory;
import org.apache.dubbo.registry.zookeeper.ZookeeperRegistry;
import org.apache.dubbo.rpc.cluster.Directory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 * @date 2021/2/1 11:40 上午
 */
public class DubboServer {

    /**
     * 服务端最核心的对象
     * ApplicationConfig
     * ProtocolConfig
     * RegistryConfig
     * ServiceConfig
     *
     * 1、配置 2、暴漏服务
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // 开始 暴漏UserService服务
        //基于application
        //协议 protocol -dubbo 协议
        //registers
        //service
        ApplicationConfig applicationConfig = new ApplicationConfig("sample-app");
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(-1); //20880

        //RegistryService
        //Directory
        //RegistryDirectory
        //ZookeeperRegistry

       // RegistryConfig registryConfig = new RegistryConfig(RegistryConfig.NO_AVAILABLE); //空的注册中心
        //RegistryConfig registryConfig = new RegistryConfig("multicast://224.1.1.1:3333"); //广播注册中心
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://192.168.0.147:2181");
        //RegistryConfig registryConfig = new RegistryConfig("redis://192.168.0.147:6379");

        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setInterface("com.client.UserService"); //暴漏的服务
        serviceConfig.setRef(UserServiceImpl.class);
        serviceConfig.setTimeout(3000);//超时时间
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setGroup("xing");
        serviceConfig.setApplication(applicationConfig);
        setLoadbalance(serviceConfig);
        serviceConfig.export();

        System.out.printf("服务已暴漏");
        System.in.read();//防止程序关闭
    }

    /**
     * 轮询
     * @param serviceConfig
     */
    public static void setLoadbalance(ServiceConfig serviceConfig){
        //serviceConfig.setLoadbalance("random");//
        serviceConfig.setLoadbalance("consistenthash");//一致性hash算法
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName("findUser");
        Map<String, String> paramter = new HashMap<String, String>();
        paramter.put("hash.arguments", "0,1");//第0和1个下标的参数进行一致性hash
        paramter.put("hash.nodes", "320");
        methodConfig.setParameters(paramter);
        serviceConfig.setMethods(Arrays.asList(methodConfig));
        //serviceConfig.setWeight(200);
    }
}
