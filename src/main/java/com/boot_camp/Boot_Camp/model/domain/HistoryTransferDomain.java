package com.boot_camp.Boot_Camp.model.domain;

import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class HistoryTransferDomain {

    private String state; // withdrawal and deposit
    private String date;
    private String opposite;
    private int point;

    public HistoryTransferDomain() {
    }

    public HistoryTransferDomain(HistoryTransferEntity e) {
        this.state = e.getState();
        this.date = e.getDate();
        this.point = e.getPoint();
        this.opposite = e.getOpposite();
    }
}
