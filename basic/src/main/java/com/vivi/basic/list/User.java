package com.vivi.basic.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.soap.SAAJResult;
import java.util.List;

@Data
public class User {
    private String id;
    private  String name;
    private Integer value;

    private Integer sortNum;

    private String remark;

    private List<User> userList;

    public User(String id, String name, Integer value, String remark) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.remark = remark;
    }

    public User(String id, String name, Integer value, String remark, List<User> userList) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.remark = remark;
        this.userList = userList;
    }
}
