package com.boot_camp.Boot_Camp.model.domain;

import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class ValidateTransferPointDomain{
    private boolean state =false;
    private String payee = "";
    private String message = "";

    public  ValidateTransferPointDomain(){}
    public  ValidateTransferPointDomain(MemberEntity entity){
        this.payee = entity.getName();
    }
}
