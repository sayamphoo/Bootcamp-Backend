package com.boot_camp.Boot_Camp.model.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class MenuStoreDomain {
    private String id;
    private String storePicture;
    private String location;
    private List<MenuStoreSubdomain> menuStoreSubdomains;

    public MenuStoreDomain() {}

    public MenuStoreDomain(String id,String storePicture, List<MenuStoreSubdomain> list,String location) {
        this.id = id;
        this.location = location;
        this.storePicture = storePicture;
        this.menuStoreSubdomains = list;
    }
}

