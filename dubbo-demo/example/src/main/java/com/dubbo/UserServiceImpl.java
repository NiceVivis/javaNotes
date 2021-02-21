package com.dubbo;

import com.client.User;
import com.client.UserService;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangwei
 * @date 2021/1/27 6:10 下午
 */
public class UserServiceImpl implements UserService {


    @Override
    public User getUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("hua"+ManagementFactory.getRuntimeMXBean().getName());
        user.setSex("nv");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //获取本机的进程id
        //ManagementFactory.getRuntimeMXBean().getName();
        return user;
    }

    @Override
    public List<User> findUser(String city, String sex) {
        return Arrays.asList(getUser(1));
    }
}
