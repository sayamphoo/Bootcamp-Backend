package com.boot_camp.Boot_Camp.model.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Setter
@Getter
public class BuyPaymentConfirmWrapper {
    private String idPayment;
    @Field(type = FieldType.Integer)
    private int amount;
    private String pass;
}
