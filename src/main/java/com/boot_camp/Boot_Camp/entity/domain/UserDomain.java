package com.boot_camp.Boot_Camp.entity.domain;

import com.boot_camp.Boot_Camp.service.StatusCodeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@Getter
@Setter
public class UserDomain {
    private int code = StatusCodeEnum.NOT_FOUND.getValue();
    private String accessToken = "";
}
