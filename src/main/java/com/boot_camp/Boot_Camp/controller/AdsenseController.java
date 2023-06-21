package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.PromotionDomain;
import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.model.wrapper.PromotionWrapper;
import com.boot_camp.Boot_Camp.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api/v1/adsense")
public class AdsenseController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/promotion")
    public List<PromotionDomain> promotion(HttpServletRequest req) {
        String id = req.getAttribute("id").toString();
        return promotionService.promotion();
    }

    @PostMapping("/promotion")
    public UtilDomain promotion(PromotionWrapper w, HttpServletRequest req) {
        String id = req.getAttribute("id").toString();
        return promotionService.promotion(w);
    }
}
