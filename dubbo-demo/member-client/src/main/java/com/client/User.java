package com.client;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangwei
 * @date 2021/1/27 6:05 下午
 */
@Data
public class User implements Serializable {

    private Integer id;

    private String name;

    private String sex;

}
