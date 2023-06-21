package com.boot_camp.Boot_Camp.model.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class StatisticsDomain {
    private String id;
    private String nameMenu;
    private String picture;
    private int exchange;
    private int receive;
    private int point;
    private int alreadySold;
}
