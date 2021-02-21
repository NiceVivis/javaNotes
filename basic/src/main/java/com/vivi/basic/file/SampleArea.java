package com.vivi.basic.file;

import com.alibaba.druid.VERSION;
import lombok.Data;

/**
 * @author yangwei
 * @date 2021/1/22 3:32 下午
 */
@Data
public class SampleArea {

    private String id;
    private String regionId;
    private String regionCode;
    private String regionName;
    private String regionType;

    public SampleArea(String id, String regionId, String regionCode, String regionName, String regionType) {
        this.id = id;
        this.regionId = regionId;
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.regionType = regionType;
    }
}
