package com.boot_camp.Boot_Camp.model.domain;


import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuStoreSubdomain {
    private String id;
    private String nameMenu;
    private String pictures;
    private int price;
    private int exchange;
    private int receive;
    private int point;
    private int category;

    public MenuStoreSubdomain(StoreMenuEntity entity) {
        this.id = entity.getId();
        this.nameMenu = entity.getNameMenu();
        this.pictures = entity.getPictures();
        this.price = entity.getPrice();
        this.exchange = entity.getExchange();
        this.receive = entity.getReceive();
        this.category = entity.getCategory();
    }
}
