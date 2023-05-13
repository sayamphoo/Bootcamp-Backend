package com.boot_camp.Boot_Camp.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Getter
@Setter
@Document(indexName = "history-transfer")
public class HistoryTransferEntity {
    @Id
    @ReadOnlyProperty
    private String id;

    @Field(type = FieldType.Text, name = "memberId")
    private String memberID;

    @Field(type = FieldType.Text, name = "state")
    private String state;

    @Field(type = FieldType.Date, name = "date", format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date date;

    @Field(type = FieldType.Integer, name = "point")
    private int point;

}
