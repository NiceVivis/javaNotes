package com.vivi.basic.mybatisplus;

import lombok.Data;

/**
 * @author yangwei
 * @date 2021/2/3 10:27 上午
 */
@Data
public class UserSelect {

    private int id;

    private String username;

    private int age;

    private String email;

    private String createTime;

    private String parentId;


}
