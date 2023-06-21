package com.boot_camp.Boot_Camp.model.entity;

import com.boot_camp.Boot_Camp.model.wrapper.EditPersonalWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "member")
@Getter
@Setter
public class MemberEntity {
    @Id
    @ReadOnlyProperty
    private String id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Text, name = "username")
    private String username;

    @Field(type = FieldType.Text, name = "password")
    private String password;


    @Field(type = FieldType.Date,
            format = DateFormat.date_optional_time,
            name = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate birthday;

    @Field(type = FieldType.Boolean, name = "store")
    private boolean store;

    @Field(type = FieldType.Text, name = "picture")
    private String picture;

    @Field(type = FieldType.Text, name = "sex")
    private String sex;

    @Field(type = FieldType.Integer, name = "point")
    private int point;

    @Field(type = FieldType.Boolean,name = "isActive")
    private boolean isActive;

    public void editPersonal(EditPersonalWrapper wrapper) {
        this.name = wrapper.getName();
        this.username = wrapper.getUsername();
        this.birthday = wrapper.getBirthday();
        this.sex = wrapper.getSex();
        this.isActive = true;
    }
}

