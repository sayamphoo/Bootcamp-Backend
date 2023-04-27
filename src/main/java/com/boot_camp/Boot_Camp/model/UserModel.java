package com.boot_camp.Boot_Camp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "member")
public class UserModel {
    @Id
    @ReadOnlyProperty
    private String id;
    @Field(type = FieldType.Text,name = "username")
    private String userName;
   // @Field(type = FieldType.Text,name = "pass")
    private String password;

    @Field(type = FieldType.Text,name = "position")
    private String position;

    @Field(type = FieldType.Integer,name = "point")
    private int point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
