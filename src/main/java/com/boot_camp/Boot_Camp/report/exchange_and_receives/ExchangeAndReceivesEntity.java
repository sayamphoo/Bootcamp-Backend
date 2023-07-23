package com.boot_camp.Boot_Camp.report.exchange_and_receives;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;

@Document(indexName = "ExchangeAndReceivesService")
@Setter
@Getter
public class ExchangeAndReceivesEntity {
    @Id
    @ReadOnlyProperty
    private String id;
    @Field(type = FieldType.Text)
    private String idStore;
    @Field(type = FieldType.Text)
    private String state;
    @Field(type = FieldType.Integer)
    private int point;
    private ArrayList<String> menu;

    public ExchangeAndReceivesEntity() {}

    public ExchangeAndReceivesEntity(
            String idStore,
            String state,
            int point,
            ArrayList<String> menu
    ) {
        this.idStore = idStore;
        this.state = state;
        this.point = point;
    }
}
