package com.boot_camp.Boot_Camp.entity.domain;

import com.boot_camp.Boot_Camp.service.StatusCodeEnum;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class LoginDomain {
    private int code = StatusCodeEnum.NOT_FOUND.getValue();
    private String accessToken = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
