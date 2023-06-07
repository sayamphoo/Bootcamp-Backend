package com.boot_camp.Boot_Camp.services;

import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ComponentService {
    public LocalDate coverStrToLocaltime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, formatter);
    }
}
