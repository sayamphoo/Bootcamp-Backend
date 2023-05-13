package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import com.boot_camp.Boot_Camp.repository.HistoryTransferRepository;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilService {
    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private Security security;

    @Autowired
    HistoryTransferRepository historyTransferRepo;

    public String searchDatabaseID(String id_member) {
        return memberRepo.findByIdMember(id_member).getId();
    }

    public void transferSaveHistory(List<HistoryTransferEntity> history) {
        historyTransferRepo.saveAll(history);
    }
}
