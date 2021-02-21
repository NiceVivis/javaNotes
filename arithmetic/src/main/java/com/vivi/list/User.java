package com.vivi.list;

import lombok.Data;

@Data
public class User {

    private Integer id;

    private String name;


    private String phone;

    public User(){

    }

    public User(Integer id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}
