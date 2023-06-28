package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.enums.CategoryLockerId;
import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import com.boot_camp.Boot_Camp.model.entity.LockerIdEntity;
import com.boot_camp.Boot_Camp.repository.*;
import com.boot_camp.Boot_Camp.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UtilService {

    @Autowired
    private Security security;

    @Autowired
    private HistoryTransferRepository historyTransferRepo;
    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private StoreRepository storeRepository;


    @Autowired
    private StatisticsMenuRepository statisticsMenuRepository;

    @Autowired
    private LockerIdRepository idLockerRepo;

    public void checkActive(boolean active) {
        if (!active) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This Username is already taken");
    }

    public  LocalDate coverStrToLocaltime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, formatter);
    }

    public void transferSaveHistory(List<HistoryTransferEntity> history) {
        historyTransferRepo.saveAll(history);
    }

    public int calculateAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    public void saveLocker(String category,String idRecord) {
        LockerIdEntity entity = new LockerIdEntity(idRecord);
        entity.setIdLocker(security.buildIdLocker(category));
        idLockerRepo.save(entity);
    }

    public String getIdRecord(String idLocker) {
        LockerIdEntity entity = idLockerRepo.findIdRecordByIdLocker(idLocker);
        if (entity != null) {
            return entity.getIdRecord();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Not Found");
        }
    }

    public String getIdLocker(String idRecord) {
        LockerIdEntity entity = idLockerRepo.findIdLockerByIdRecord(idRecord);
        if (entity != null) {
            return entity.getIdLocker();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Not Found");
        }
    }
}
