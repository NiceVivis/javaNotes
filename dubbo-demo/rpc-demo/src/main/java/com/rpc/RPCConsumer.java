package com.rpc;

import com.rpc.framework.RPCFramework;
import com.rpc.service.RPCService;

/**
 * @author yangwei
 * @date 2021/1/29 2:44 下午
 */
public class RPCConsumer {

    public static void main(String[] args) {
        //服务调用者只需要设置依赖
        RPCService service = RPCFramework.refer(RPCService.class,"127.0.0.1",2333);
        service.hello("xxx");
    }
}
