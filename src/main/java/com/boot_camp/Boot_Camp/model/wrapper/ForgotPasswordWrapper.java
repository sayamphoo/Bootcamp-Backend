package com.boot_camp.Boot_Camp.model.wrapper;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordWrapper {
    private String username;
    private String birthday;
    private String newPassword;
}
