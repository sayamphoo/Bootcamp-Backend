package com.boot_camp.Boot_Camp.model.domain;


import com.boot_camp.Boot_Camp.enums.StatusCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDomain {
    private int code = StatusCodeEnum.NOT_FOUND.getValue();
    private int point = -1;
}
