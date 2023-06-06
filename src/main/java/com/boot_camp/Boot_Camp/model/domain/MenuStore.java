package com.boot_camp.Boot_Camp.model.domain;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuStore {
    private String nameMenu;
    private String pictures;
    private int price;
    private int exchange;
    private int receive;
    private int point;

    public MenuStore(String nameMenu, int price, int exchange, int receive, String pictures) {
        this.nameMenu = nameMenu;
        this.pictures = pictures;
        this.price = price;
        this.exchange = exchange;
        this.receive = receive;
    }
}