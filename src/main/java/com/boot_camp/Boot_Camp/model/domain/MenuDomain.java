package com.boot_camp.Boot_Camp.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDomain {
    private String id;
    private String nameMenu;
    private String picture;

    public MenuDomain(String id, String nameMenu, String pictures) {
        this.id = id;
        this.nameMenu = nameMenu;
        this.picture = pictures;
    }
}
