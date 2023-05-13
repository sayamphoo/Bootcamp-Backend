package com.boot_camp.Boot_Camp.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@Document(indexName = "store-menu")
public class StoreMenuEntity {
    @Id
    @ReadOnlyProperty
    private String id;

    @Field(type = FieldType.Text, name = "memberId")
    private String memberId;

    @Field(type = FieldType.Text, name = "nameMenu")
    private String nameMenu;

    @Field(type = FieldType.Text, name = "pictures")
    private List<String> pictures;

    public StoreMenuEntity() {}

    public StoreMenuEntity(String memberId, String nameMenu, List<String> pictures) {
        this.memberId = memberId;
        this.nameMenu = nameMenu;
        this.pictures = pictures;
    }
}