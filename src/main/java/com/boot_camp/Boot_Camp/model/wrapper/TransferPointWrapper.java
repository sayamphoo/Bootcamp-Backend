package com.boot_camp.Boot_Camp.model.wrapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferPointWrapper {
    private String originID;
    private String payee;
    private int point;
}
