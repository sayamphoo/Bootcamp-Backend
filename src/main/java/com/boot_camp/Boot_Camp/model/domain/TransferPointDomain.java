package com.boot_camp.Boot_Camp.model.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class TransferPointDomain {
    private String message;
    private String state; // withdrawal and deposit
    private String date;
    private String payee;
    private String origin;
    private int point;
    private int balance;
    private int fee = 0;
    private ArrayList<MenuStoreSubdomain> menus = new ArrayList<>();
}
