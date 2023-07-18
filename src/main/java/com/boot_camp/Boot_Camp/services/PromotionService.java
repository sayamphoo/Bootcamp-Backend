package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.model.entity.PromotionEntity;
import com.boot_camp.Boot_Camp.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepo;

    @Autowired
    private MembersService membersService;

    @Autowired
    private UtilService utilService;

    public UtilDomain addPromotion(String name, String code, String description, MultipartFile file)  {
        PromotionEntity entity = new PromotionEntity();
        entity.setName(name);
        entity.setCoed(code);
        entity.setDescription(description);
        try{
            entity.setPicture(new SaveFileService().saveImage(file));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }

        promotionRepo.save(entity);

        return new UtilDomain(HttpStatus.OK.value(),"Success");
    }


}

