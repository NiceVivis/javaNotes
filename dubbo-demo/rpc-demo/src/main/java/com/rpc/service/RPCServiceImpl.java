package com.rpc.service;

/**
 * @author yangwei
 * @date 2021/1/29 11:37 上午
 */
public class RPCServiceImpl implements RPCService{

    @Override
    public String hello(String name) {
        return "hello world";
    }
}
