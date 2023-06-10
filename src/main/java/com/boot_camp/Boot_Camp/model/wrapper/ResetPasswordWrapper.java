package com.boot_camp.Boot_Camp.model.wrapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordWrapper {
    private String id;
    private String oldPassword;
    private String newPassword;
}
