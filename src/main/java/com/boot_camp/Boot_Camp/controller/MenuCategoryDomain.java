package com.boot_camp.Boot_Camp.controller;


import com.boot_camp.Boot_Camp.model.domain.MenuDomain;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuCategoryDomain {
    private String id;
    private String nameStore;
    private List<MenuDomain> menuDomains;

    public MenuCategoryDomain(String idAccount, String name, List<MenuDomain> menuDomains) {
        this.id = idAccount;
        this.nameStore = name;
        this.menuDomains = menuDomains;
    }
}

