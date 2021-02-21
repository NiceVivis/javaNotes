package com.client;

import java.util.List;

/**
 * @author yangwei
 * @date 2021/1/27 6:05 下午
 */
public interface UserService {

    User getUser(Integer id);

    List<User> findUser(String city,String sex);

}
