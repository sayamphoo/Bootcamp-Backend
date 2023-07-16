package com.boot_camp.Boot_Camp.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubBuyMenuEntity {
    private String idMenu;
    private int amount;

    public SubBuyMenuEntity(String idR,int amount) {
        this.amount = amount;
        this.idMenu = idR;
    }

    public SubBuyMenuEntity( ) {

    }
}

