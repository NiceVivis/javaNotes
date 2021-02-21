package com.rocketmq.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class OrderExt implements Serializable {

    private String id;

    private Date createTime;

    private Long money;

    private String title;
}