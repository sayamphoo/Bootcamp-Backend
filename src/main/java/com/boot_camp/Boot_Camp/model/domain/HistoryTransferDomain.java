package com.boot_camp.Boot_Camp.model.domain;

import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class HistoryTransferDomain {
    //    state : String
//    payee : String
//    date : String
//    point : int

    private String state; // withdrawal and deposit
    private Date date;
    private int point;


    public HistoryTransferDomain() {
    }

    public HistoryTransferDomain(HistoryTransferEntity e) {
        this.state = e.getState();
        this.date = e.getDate();
        this.point = e.getPoint();
    }
}
