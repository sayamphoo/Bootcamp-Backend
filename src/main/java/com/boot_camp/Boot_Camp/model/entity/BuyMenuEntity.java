package com.boot_camp.Boot_Camp.model.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Document(indexName = "buy_menu")
public class BuyMenuEntity {
    @Id
    @ReadOnlyProperty
    private String id;
    private String idRecord; //store
    private ArrayList<SubBuyMenuEntity> menu;
    private int amount;
    private String state;
    private boolean isScan;
    private boolean paymentComplete;
}

