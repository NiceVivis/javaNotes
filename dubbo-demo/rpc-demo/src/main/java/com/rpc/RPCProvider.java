package com.rpc;

import com.rpc.framework.RPCFramework;
import com.rpc.service.RPCService;
import com.rpc.service.RPCServiceImpl;

/**
 * @author yangwei
 * @date 2021/1/29 2:03 下午
 */
public class RPCProvider {

    public static void main(String[] args) throws Exception {
        //服务提供者只需要暴漏出接口
        RPCService service = new RPCServiceImpl();

        RPCFramework.export(service,2333);

        //服务调用者只需要设置依赖
    }


}
