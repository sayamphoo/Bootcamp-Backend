package com.boot_camp.Boot_Camp.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "payment")
@Getter
@Setter
public class PaymentEntity {
    @Id
    @ReadOnlyProperty
    private String id; //lockerID
    @Field(type = FieldType.Integer, name = "price")
    private double price;
    @Field(type = FieldType.Integer, name = "point")
    private int point;

    public PaymentEntity(int price,int point) {
        this.price = price;
        this.point = point;
    }
}
