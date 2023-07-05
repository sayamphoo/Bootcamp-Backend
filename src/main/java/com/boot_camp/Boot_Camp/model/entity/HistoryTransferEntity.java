package com.boot_camp.Boot_Camp.model.entity;

import com.boot_camp.Boot_Camp.services.HistoryTransferService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName = "history-transfer")
public class HistoryTransferEntity {
    @Id
    @ReadOnlyProperty
    private String id;

    @Field(type = FieldType.Text, name = "accountId")
    private String accountId;

    @Field(type = FieldType.Text,name = "transactionId")
    private String transactionId;

    @Field(type = FieldType.Text, name = "opposite")
    private String opposite;

    @Field(type = FieldType.Text, name = "state")
    private String state;

    @Field(type = FieldType.Text, name = "date")
    private String date;

    @Field(type = FieldType.Integer, name = "point")
    private int point;

    public HistoryTransferEntity(){}

    public HistoryTransferEntity(String accountId,String opposite ,String state,int point) {
        this.accountId = accountId;
        this.opposite = opposite;
        this.state = state;
        this.point = point;
    }

}
