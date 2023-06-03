package com.boot_camp.Boot_Camp.model.domain;

import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
public class ValidateDomain {
    private Boolean valid;
    private String message;
    private String token;
}
