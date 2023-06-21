package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.model.domain.PromotionDomain;
import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.model.entity.PromotionEntity;
import com.boot_camp.Boot_Camp.model.wrapper.PromotionWrapper;
import com.boot_camp.Boot_Camp.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepo;

    @Autowired
    private UtilService utilService;

    //Get
    public List<PromotionDomain> promotion() {
        List<PromotionDomain> domains = new ArrayList<>();
        Iterable<PromotionEntity> entity = promotionRepo.findAll();

        entity.forEach(e -> {
            if (!e.getIsActive()){
                return;
            }
            e.setId(utilService.getIdLocker(e.getId()));
            domains.add(new PromotionDomain(e));
        });

        return domains;
    }

    //Post
    public UtilDomain promotion(PromotionWrapper w) {
        PromotionEntity entity = new PromotionEntity(w);
        promotionRepo.save(entity);
        utilService.saveLocker("3", entity.getId());
        return new UtilDomain(HttpStatus.OK.value(), "Success");
    }

}
