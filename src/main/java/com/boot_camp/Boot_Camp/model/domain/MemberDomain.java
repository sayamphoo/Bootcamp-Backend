package com.boot_camp.Boot_Camp.model.domain;

import com.boot_camp.Boot_Camp.enums.StatusCodeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@Getter
@Setter
public class MemberDomain {
    private int code = StatusCodeEnum.NOT_FOUND.getValue();
    private String accessToken = "";
}
