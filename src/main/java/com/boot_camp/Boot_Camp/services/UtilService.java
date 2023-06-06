package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.repository.HistoryTransferRepository;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.repository.ShopsRepository;
import com.boot_camp.Boot_Camp.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
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
    private ShopsRepository shopsRepository;


    public String searchDatabaseID(String id_member) {
        MemberEntity memberEntity = memberRepo.findByIdAccount(id_member);
        if (memberEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.getReasonPhrase());
        }
        return memberEntity.getId();
    }

    public void transferSaveHistory(List<HistoryTransferEntity> history) {
        historyTransferRepo.saveAll(history);
    }

//    @PostConstruct
    public void delete() {
        historyTransferRepo.deleteAll();
        shopsRepository.deleteAll();
        memberRepo.deleteAll();
    }


}
