package com.boot_camp.Boot_Camp.model.entity;


import com.boot_camp.Boot_Camp.model.domain.PromotionDomain;
import com.boot_camp.Boot_Camp.model.wrapper.PromotionWrapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName = "promotion")
public class PromotionEntity {
    @Id
    @ReadOnlyProperty
    private String id;

    @Field(type = FieldType.Text, name = "picture")
    private String picture;

    @Field(type = FieldType.Text,name = "title")
    private String title;

    @Field(type = FieldType.Text,name = "expired")
    private String expired;

    @Field(type = FieldType.Text,name = "description")
    private String description;

    @Field(type = FieldType.Boolean,name = "isActive")
    private Boolean isActive;

    public PromotionEntity(PromotionWrapper w) {
        this.picture = w.getPicture();
        this.title =  w.getTitle();
        this.expired = w.getExpired();
        this.description = w.getDescription();
        this.isActive = true;
    }
}
