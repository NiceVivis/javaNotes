package com.vivi.basic.Json;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yangwei
 * @date 2020/9/15 5:44 下午
 */
@NoArgsConstructor
@Data
public class S60SpecialDTO implements Serializable {

    private String BannerTime;

    private String BannerPic1;

    private String BannerUrl1;

    private String BannerPic2;

    private String BannerUrl2;
}
