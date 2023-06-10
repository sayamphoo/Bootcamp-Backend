package com.boot_camp.Boot_Camp.model.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransferPointDomain {
    private int code;
    private String message;
    private String state; // withdrawal and deposit
    private String date;
    private String payee;
    private int point;
    private int balance;
}
