package com.boot_camp.Boot_Camp.model.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
public class BuildQrcodeForMenuWrapper {
    private String state;
    private Map<String,Integer> menu;
}
