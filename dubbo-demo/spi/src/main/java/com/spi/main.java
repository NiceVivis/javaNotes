package com.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author yangwei
 * @date 2021/1/27 4:33 下午
 */
public class main {

    public static void main(String[] args) {
        Iterator<UserService> iterator = ServiceLoader.load(UserService.class).iterator();
        UserService service = iterator.next();
        System.out.println(service.getname(11));
    }
}
