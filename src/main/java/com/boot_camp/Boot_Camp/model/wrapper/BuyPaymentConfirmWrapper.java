package com.boot_camp.Boot_Camp.model.wrapper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BuyPaymentConfirmWrapper {
    private String idPayment;
    private int amount;
    private String pass;
}
