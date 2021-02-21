package com.vivi.basic.sql.area;

import lombok.Data;

import java.util.List;

/**
 * @author yangwei
 * @date 2021/1/22 2:10 下午
 */
@Data
public class Areas {

    private String code;

    private String name;

    private List<Areas> cityList;

    private List<Areas> areaList;
}
