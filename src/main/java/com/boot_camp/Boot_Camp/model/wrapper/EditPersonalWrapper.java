package com.boot_camp.Boot_Camp.model.wrapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Setter
@Getter
public class EditPersonalWrapper {
    private String name;
    private String username;
    private LocalDate birthday;
    private String sex;
}
