package com.vivi.basic.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    private Integer id;

    private String goodsName;

    private String remark;

    private String numbet;

    private double price;

    private String userId;

    private String userName;
}
