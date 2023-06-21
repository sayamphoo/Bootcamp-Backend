package com.boot_camp.Boot_Camp.model.domain;

import com.boot_camp.Boot_Camp.model.entity.BuyMenuEntity;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter

public class BuyMenuDomain {
    private ArrayList<String> idMenu;
    private int amount;

    public BuyMenuDomain(BuyMenuEntity entity) {
        this.idMenu = entity.getIdMenu();
        this.amount = entity.getAmount();
    }
}
