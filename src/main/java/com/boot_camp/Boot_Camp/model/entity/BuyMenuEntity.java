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
    private String idRecord; //store
    private ArrayList<String> idMenu; // idRecord
    private int amount;
    private String state;
    private boolean isScan;
}
