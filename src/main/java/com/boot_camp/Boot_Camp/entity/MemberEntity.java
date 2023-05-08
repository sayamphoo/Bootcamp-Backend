package com.boot_camp.Boot_Camp.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Random;

@Document(indexName = "member")
@Getter
@Setter
public class MemberEntity {
    @Id
    @ReadOnlyProperty
    private String id;

    private int ids;

    @Field(type = FieldType.Text, name = "username")
    private String username;

    @Field(type = FieldType.Text, name = "pass")
    private String password;

    @Field(type = FieldType.Date, name = "birthday")
    private String birthday;

    @Field(type = FieldType.Text, name = "position")
    private String position;

    @Field(type = FieldType.Text, name = "sex")
    private String sex;

    @Field(type = FieldType.Integer, name = "point")
    private int point = 0;

}
