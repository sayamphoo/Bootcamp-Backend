package com.boot_camp.Boot_Camp.model.wrapper;

import com.boot_camp.Boot_Camp.model.entity.PromotionEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionWrapper {
    private String picture;
    private String title;
    private String expired;
    private String description;

    public PromotionWrapper(PromotionEntity entity) {
        this.picture = entity.getPicture();
        this.title = entity.getTitle();
        this.expired = entity.getExpired();
        this.description = entity.getDescription();
    }
}
