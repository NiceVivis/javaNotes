package com.dubbo.boot;

import com.client.User;
import com.client.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;

/**
 * @author yangwei
 * @date 2021/1/27 6:10 下午
 */
@Service
@Component
public class UserServiceImpl implements UserService {


    @Override
    public User getUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("hua"+ManagementFactory.getRuntimeMXBean().getName());
        user.setSex("nv");

        //获取本机的进程id
        //ManagementFactory.getRuntimeMXBean().getName();
        return user;
    }
}
