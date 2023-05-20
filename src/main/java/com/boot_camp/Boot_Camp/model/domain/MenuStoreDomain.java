package com.boot_camp.Boot_Camp.model.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class MenuStoreDomain {
    private String storePicture;
    private List<MenuStore> menuStores;

    public MenuStoreDomain() {}

    public MenuStoreDomain(String storePicture, List<MenuStore> list) {
        this.menuStores = list;
    }
}

