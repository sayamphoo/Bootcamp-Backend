package com.boot_camp.Boot_Camp.entity.domain;


import com.boot_camp.Boot_Camp.service.StatusCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDomain {
    private int code = StatusCodeEnum.NOT_FOUND.getValue();
    private int point = -1;
}
