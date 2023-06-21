package com.boot_camp.Boot_Camp.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;

@EntityScan
@Getter
@Setter
public class MemberDomain {
    private String message;
    private boolean store;
    private String accessToken = "";
    private String accountId;

}
