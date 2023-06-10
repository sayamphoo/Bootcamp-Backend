package com.boot_camp.Boot_Camp.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtilStoreDomain {
    private int code;
    private String message;


    public UtilStoreDomain(int code , String message) {
        this.code = code;
        this.message = message;
    }
}
