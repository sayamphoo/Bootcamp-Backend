package com.boot_camp.Boot_Camp.model.domain;

import com.boot_camp.Boot_Camp.model.entity.BuyMenuEntity;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class BuyMenuDomain {
    private ArrayList<MenuStoreSubdomain> menu;
    private int amount;

    public BuyMenuDomain(BuyMenuEntity entity, ArrayList<MenuStoreSubdomain> lists) {
        this.amount = entity.getAmount();
        this.menu = lists;
    }
}
