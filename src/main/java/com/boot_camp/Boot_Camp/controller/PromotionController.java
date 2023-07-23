package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.PromotionDomain;
import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @PostMapping("/add-promotion")
    public UtilDomain addPromotion(
            @RequestParam("name") String name,
            @RequestParam("code") String code,
            @RequestParam("type") String type,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest req
            ) {

        return promotionService.addPromotion(name,code,type,file);
    }
}
