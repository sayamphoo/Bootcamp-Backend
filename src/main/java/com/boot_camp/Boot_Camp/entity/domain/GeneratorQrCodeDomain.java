package com.boot_camp.Boot_Camp.entity.domain;

import lombok.Getter;
import lombok.Setter;
import java.awt.image.BufferedImage;

@Getter
@Setter
public class GeneratorQrCodeDomain {
    int code = 200;
    BufferedImage bufferedImage;
}
