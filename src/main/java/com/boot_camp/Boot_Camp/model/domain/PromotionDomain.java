package com.boot_camp.Boot_Camp.model.domain;


import com.boot_camp.Boot_Camp.model.entity.PromotionEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
public class PromotionDomain {
    private String name;
    private String code;
    private String description;
    private String picture;
    private Boolean isActivate;


    public PromotionDomain(PromotionEntity entity) {
        this.name = entity.getName();
        this.picture = entity.getPicture();
        this.code =entity.getCoed();
        this.description = entity.getDescription();
        this.isActivate = entity.getIsActivate();
    }
}
