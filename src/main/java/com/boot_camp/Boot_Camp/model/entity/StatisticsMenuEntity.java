package com.boot_camp.Boot_Camp.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(indexName = "statistics-menu")
public class StatisticsMenuEntity {
    @Id
    @ReadOnlyProperty
    private String id;

    @Field(type = FieldType.Text,name = "storeId")
    private String storeId;

    @Field(type = FieldType.Text,name = "idAccount")
    private String idAccount;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time, name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private LocalDateTime dateTime;
}
