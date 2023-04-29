package com.boot_camp.Boot_Camp.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "member")

public class UserEntity {
    @Id
    @ReadOnlyProperty
    private String id;
    @Field(type = FieldType.Text,name = "username")
    private String username;
    @Field(type = FieldType.Text,name = "pass")
    private String password;

    @Field(type = FieldType.Text,name = "position")
    private String position;

    @Field(type = FieldType.Integer,name = "point")
    private int point = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
