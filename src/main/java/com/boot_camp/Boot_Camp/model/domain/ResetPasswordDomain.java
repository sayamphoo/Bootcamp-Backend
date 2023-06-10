package com.boot_camp.Boot_Camp.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDomain {
    private String code;
    private String message;
}
