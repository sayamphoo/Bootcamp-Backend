package com.boot_camp.Boot_Camp.model.wrapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferPointWrapper {
    private String originID;
    private String payee;
    private int point;

    public TransferPointWrapper(String originID,String payee,int point) {
        this.originID = originID;
        this.payee = payee;
        this.point = point;
    }
}
