package com.vivi.basic.Json;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangwei
 * @date 2020/9/15 5:43 下午
 */
@Data
public class PopWindowsDTO implements Serializable {

    private HomeDTO Home;

    private S60SpecialDTO S60Special;

    private S90DTO S90List;

    private List<S60VideoIconDTO> S60VideoIcon;
}
