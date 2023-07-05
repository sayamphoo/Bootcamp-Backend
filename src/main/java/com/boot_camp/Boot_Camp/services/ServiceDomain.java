package com.boot_camp.Boot_Camp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDomain {
    @Autowired
    private static MembersService membersService;

    @Autowired
    private static HistoryTransferService historyTransferService;

    public static MembersService getMembersService() {
        return membersService;
    }

    public static void setMembersService(MembersService membersService) {
        ServiceDomain.membersService = membersService;
    }

    public static HistoryTransferService getHistoryTransferService() {
        return historyTransferService;
    }

    public static void setHistoryTransferService(HistoryTransferService historyTransferService) {
        ServiceDomain.historyTransferService = historyTransferService;
    }
}
