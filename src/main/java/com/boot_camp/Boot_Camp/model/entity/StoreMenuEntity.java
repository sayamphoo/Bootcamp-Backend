package com.boot_camp.Boot_Camp.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Getter
@Setter
@Document(indexName = "store-menu")
public class StoreMenuEntity {
    @Id
    @ReadOnlyProperty
    private String id;

    @Field(type = FieldType.Text, name = "idAccount")
    private String idAccount;

    @Field(type = FieldType.Text, name = "nameMenu")
    private String nameMenu;

    @Field(type = FieldType.Integer, name = "price")
    private int price;

    @Field(type = FieldType.Integer, name = "exchange")
    private int exchange;

    @Field(type = FieldType.Integer, name = "receive")
    private int receive;

    @Field(type = FieldType.Text, name = "pictures")
    private String pictures;

    @Field(type = FieldType.Integer, name = "category")
    private int category;

    public StoreMenuEntity() {
    }

    public StoreMenuEntity(String memberId, String nameMenu, int price, int exchange, int receive, String pictures,int category) {
        this.idAccount = memberId;
        this.nameMenu = nameMenu;
        this.pictures = pictures;
        this.price = price;
        this.receive = receive;
        this.exchange = exchange;
        this.category = category;
    }
}