package com.boot_camp.Boot_Camp.model.domain;


import com.boot_camp.Boot_Camp.model.entity.PromotionEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionDomain {
    private String id;
    private String picture;
    private String title;
    private String expired;
    private String description;

    public PromotionDomain(PromotionEntity entity) {
        this.id = entity.getId();
        this.picture = entity.getPicture();
        this.title = entity.getTitle();
        this.expired = entity.getExpired();
        this.description = entity.getDescription();
    }
}
