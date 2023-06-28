package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.enums.ExceptionTitleEnum;
import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.entity.PromotionEntity;
import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import com.boot_camp.Boot_Camp.repository.LockerIdRepository;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.repository.PromotionRepository;
import com.boot_camp.Boot_Camp.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class DeleteService {
    @Autowired
    private LockerIdRepository lockerIdRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private StoreRepository storeRepo;

    @Autowired
    private PromotionRepository promotionRepo;

    @Autowired
    private UtilService utilService;

    public UtilDomain remove(String idLocker) {

        switch (idLocker.substring(0, 1)) {
            case "1":
                return removeAccount(utilService.getIdRecord(idLocker));
            case "2":
                return removeMenu(utilService.getIdRecord(idLocker));
            case "3":
                return removePromotion(utilService.getIdRecord(idLocker));
            default:
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "unknown user");
        }
    }

    private UtilDomain removePromotion(String idRecord) {

        Optional<PromotionEntity> optional = promotionRepo.findById(idRecord);
        if (optional.isPresent()) {
            PromotionEntity entity = optional.get();
            entity.setIsActive(false);
            promotionRepo.save(entity);

            return new UtilDomain(HttpStatus.OK.value(),"Success");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Promotion Not Found");
        }
    }


    private UtilDomain removeAccount(String idRecord) {

        Optional<MemberEntity> optional = memberRepo.findById(idRecord);
        if (optional.isPresent()) {
            MemberEntity entity = optional.get();
            entity.setActive(false);
            memberRepo.save(entity);

            return new UtilDomain(HttpStatus.OK.value(), "Success");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionTitleEnum.MEMBER_ERROR.getTitle());
        }
    }

    private UtilDomain removeMenu(String idRecord) {

        Optional<StoreMenuEntity> optional = storeRepo.findById(idRecord);
        if (optional.isPresent()) {
            StoreMenuEntity entity = optional.get();
            entity.setActive(false);
            storeRepo.save(entity);

            return new UtilDomain(HttpStatus.OK.value(), "Success");

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionTitleEnum.MEMBER_ERROR.getTitle());
        }
    }
}
