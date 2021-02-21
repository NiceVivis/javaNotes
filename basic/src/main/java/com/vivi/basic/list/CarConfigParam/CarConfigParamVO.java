package com.vivi.basic.list.CarConfigParam;

import lombok.Data;

import java.util.List;

@Data
public class CarConfigParamVO {

    /**
     * 参数code
     */
    private String paramCode;
    /**
     * 参数级别
     */
    private Integer paramLevel;
    /**
     * 参数名称
     */
    private String paramName;
    /** 参数值 */
    private String paramValue;

    private String parentParamCode;
    /**
     * 参数定义备注
     */
    private String remark;
    /**
     * 参数值备注
     */
    private String valueRemark;

    /**
     * 排序
     */
    private Integer sortNum;

    private String IsMerge;

    /**
     * 子参数列表
     */
    private List<List<CarConfigParam>> subParamList;

}


