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
@Document(indexName = "buy-menu")
public class BuyMenuEntity {

    @Id
    @ReadOnlyProperty
    private String id;

    @Field(type = FieldType.Text,name = "storeId")
    private String storeId;

    @Field(type = FieldType.Boolean,name = "isScan")
    private Boolean isScan = false;

    @Field(type = FieldType.Text,name = "idAccount")
    private String idAccount;

    @Field(type = FieldType.Integer,name = "point")
    private int point;


}
