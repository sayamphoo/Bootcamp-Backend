package com.boot_camp.Boot_Camp.model.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PointDomain {
    private String name;
    private String id;
    private int point = -1;
}
