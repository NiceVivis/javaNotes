package com.vivi.basic.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    private String userId;

    private String userName;

    private String phone;

    private String sex;
}
