package com.boot_camp.Boot_Camp.report.Sale;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "SaleAndBuyEntity")
@Setter
@Getter

public class SaleAndBuyEntity {
    @Id
    @ReadOnlyProperty
    private String id;

    @Field(type = FieldType.Text)
    private String idStore;
    @Field(type = FieldType.Integer)
    private int price;
    @Field(type = FieldType.Integer)
    private int amount;
    @Field(type = FieldType.Integer)
    private int priceTotal;
    @Field(type = FieldType.Boolean)
    private boolean state;
    // true = sale
    // false = buy
    public SaleAndBuyEntity() {}

    public SaleAndBuyEntity(String idStore, int price, int amount, int priceTotal, boolean state) {
        this.idStore = idStore;
        this.price = price;
        this.amount = amount;
        this.priceTotal = priceTotal;
        this.state = state;
    }
}
