package com.boot_camp.Boot_Camp.model.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;

@Getter
@Setter
@Document(indexName = "buy_menu")
public class BuyMenuEntity {
    private String id;
    private String idRecord;
    private ArrayList<String> idMenu;
    private int amount;
    private boolean isScan;
}
